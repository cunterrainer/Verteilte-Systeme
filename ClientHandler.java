package de.othr.vs.quizarena.net;

import de.othr.vs.quizarena.core.QuizRoom;
import de.othr.vs.quizarena.core.QuizServerState;
import de.othr.vs.quizarena.model.PlayerSession;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Locale;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final QuizServerState state;
    private BufferedReader in;
    private PrintWriter out;
    private PlayerSession session;

    public ClientHandler(Socket socket, QuizServerState state) {
        super("client-" + socket.getPort());
        this.socket = socket;
        this.state = state;
    }

    @Override
    public void run() {
        try (Socket ignored = socket) {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            send("OK|WELCOME|Use HELLO|<username> to start.");

            String line;
            while ((line = in.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    send("ERR|EMPTY_COMMAND");
                    continue;
                }
                ProtocolParser.Command command = ProtocolParser.parse(line);
                if (handleCommand(command)) {
                    break;
                }
            }
        } catch (IOException ioException) {
            send("ERR|IO_ERROR");
        } finally {
            cleanup();
        }
    }

    private boolean handleCommand(ProtocolParser.Command command) {
        String name = command.getName();
        if ("HELLO".equals(name)) {
            return handleHello(command.getArgs());
        }
        if ("LIST_ROOMS".equals(name)) {
            return handleListRooms();
        }
        if ("CREATE_ROOM".equals(name)) {
            return handleCreateRoom(command.getArgs());
        }
        if ("JOIN_ROOM".equals(name)) {
            return handleJoinRoom(command.getArgs());
        }
        if ("READY".equals(name)) {
            return handleReady();
        }
        if ("ANSWER".equals(name)) {
            return handleAnswer(command.getArgs());
        }
        if ("LEAVE".equals(name)) {
            return handleLeave();
        }
        if ("QUIT".equals(name)) {
            return handleQuit();
        }
        send("ERR|UNKNOWN_COMMAND");
        return false;
    }

    private boolean requireSession() {
        if (session == null) {
            send("ERR|HELLO_REQUIRED");
            return false;
        }
        return true;
    }

    private boolean handleHello(String[] args) {
        if (session != null) {
            send("ERR|ALREADY_LOGGED_IN");
            return false;
        }
        if (args.length < 1 || args[0].isBlank()) {
            send("ERR|MISSING_USERNAME");
            return false;
        }
        String username = args[0].trim();
        if (!state.registerUser(username, this)) {
            send("ERR|USERNAME_IN_USE");
            return false;
        }
        session = new PlayerSession(username);
        send("OK|HELLO|" + username);
        return false;
    }

    private boolean handleListRooms() {
        if (!requireSession()) {
            return false;
        }
        List<String> rooms = state.listRooms();
        send("OK|ROOMS|" + String.join(",", rooms));
        return false;
    }

    private boolean handleCreateRoom(String[] args) {
        if (!requireSession()) {
            return false;
        }
        if (session.getRoomId() != null) {
            send("ERR|ALREADY_IN_ROOM");
            return false;
        }
        if (args.length < 1 || args[0].isBlank()) {
            send("ERR|MISSING_ROOM_NAME");
            return false;
        }
        QuizRoom room = state.createRoom(args[0], session.getUsername(), this);
        if (room == null) {
            send("ERR|ROOM_CREATE_FAILED");
            return false;
        }
        send("OK|ROOM_CREATED|" + room.getRoomId());
        room.broadcast("ROOM_JOINED|" + room.getRoomId() + "|" + room.getPlayerCount());
        room.broadcast("ROOM_UPDATE|" + room.getRoomId() + "|" + room.getPlayerCount() + "|" + room.getReadyCount());
        return false;
    }

    private boolean handleJoinRoom(String[] args) {
        if (!requireSession()) {
            return false;
        }
        if (session.getRoomId() != null) {
            send("ERR|ALREADY_IN_ROOM");
            return false;
        }
        if (args.length < 1 || args[0].isBlank()) {
            send("ERR|MISSING_ROOM_ID");
            return false;
        }
        String roomId = args[0];
        QuizRoom room = state.joinRoom(roomId, session.getUsername(), this);
        if (room == null) {
            send("ERR|ROOM_JOIN_FAILED");
            return false;
        }
        send("OK|ROOM_JOINED|" + roomId);
        room.broadcast("ROOM_JOINED|" + room.getRoomId() + "|" + room.getPlayerCount());
        room.broadcast("ROOM_UPDATE|" + room.getRoomId() + "|" + room.getPlayerCount() + "|" + room.getReadyCount());
        return false;
    }

    private boolean handleReady() {
        if (!requireSession()) {
            return false;
        }
        String roomId = session.getRoomId();
        if (roomId == null) {
            send("ERR|NOT_IN_ROOM");
            return false;
        }
        QuizRoom room = state.getRoom(roomId);
        if (room == null || !room.hasPlayer(session.getUsername())) {
            send("ERR|ROOM_NOT_FOUND");
            return false;
        }

        if (!room.markReady(session.getUsername())) {
            send("ERR|INVALID_STATE");
            return false;
        }
        session.setReady(true);
        room.broadcast("ROOM_UPDATE|" + room.getRoomId() + "|" + room.getPlayerCount() + "|" + room.getReadyCount());

        if (room.startGame()) {
            room.broadcast("OK|GAME_STARTING");
            Thread loopThread = new Thread(new RoomGameLoop(room, state, 20), "room-loop-" + room.getRoomId());
            loopThread.start();
        }
        return false;
    }

    private boolean handleAnswer(String[] args) {
        if (!requireSession()) {
            return false;
        }
        String roomId = session.getRoomId();
        if (roomId == null) {
            send("ERR|NOT_IN_ROOM");
            return false;
        }
        if (args.length < 1 || args[0].isBlank()) {
            send("ERR|MISSING_ANSWER");
            return false;
        }
        QuizRoom room = state.getRoom(roomId);
        if (room == null) {
            send("ERR|ROOM_NOT_FOUND");
            return false;
        }
        QuizRoom.SubmitStatus status = room.submitAnswer(session.getUsername(), args[0].toUpperCase(Locale.ROOT));
        switch (status) {
            case ACCEPTED:
                send("OK|ANSWER_ACCEPTED");
                break;
            case INVALID_STATE:
                send("ERR|INVALID_STATE");
                break;
            case INVALID_OPTION:
                send("ERR|INVALID_OPTION");
                break;
            case ALREADY_ANSWERED:
                send("ERR|ALREADY_ANSWERED");
                break;
            case NOT_IN_ROOM:
                send("ERR|NOT_IN_ROOM");
                break;
            default:
                send("ERR|UNKNOWN_STATE");
                break;
        }
        return false;
    }

    private boolean handleLeave() {
        if (!requireSession()) {
            return false;
        }
        QuizRoom room = state.leaveCurrentRoom(session.getUsername(), this);
        if (room == null) {
            send("ERR|NOT_IN_ROOM");
            return false;
        }
        send("OK|LEFT_ROOM");
        room.broadcast("ROOM_UPDATE|" + room.getRoomId() + "|" + room.getPlayerCount() + "|" + room.getReadyCount());
        return false;
    }

    private boolean handleQuit() {
        if (session != null) {
            state.leaveCurrentRoom(session.getUsername(), this);
            state.unregisterUser(session.getUsername());
        }
        send("BYE");
        return true;
    }

    private void cleanup() {
        if (session == null) {
            return;
        }
        state.leaveCurrentRoom(session.getUsername(), this);
        state.unregisterUser(session.getUsername());
        session = null;
    }

    public synchronized void send(String line) {
        if (out != null) {
            out.println(line);
        }
    }

    public String getRoomId() {
        return session == null ? null : session.getRoomId();
    }

    public void setRoomId(String roomId) {
        if (session != null) {
            session.setRoomId(roomId);
        }
    }

    public void setReady(boolean ready) {
        if (session != null) {
            session.setReady(ready);
        }
    }
}
