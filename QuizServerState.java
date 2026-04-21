package de.othr.vs.quizarena.core;

import de.othr.vs.quizarena.net.ClientHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizServerState {
    private final Map<String, ClientHandler> users = new ConcurrentHashMap<>();
    private final Map<String, QuizRoom> rooms = new ConcurrentHashMap<>();
    private final AtomicInteger nextRoomId = new AtomicInteger(1);
    private final QuestionBank questionBank;

    public QuizServerState(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public boolean registerUser(String username, ClientHandler handler) {
        return users.putIfAbsent(username, handler) == null;
    }

    public void unregisterUser(String username) {
        if (username == null) {
            return;
        }
        users.remove(username);
    }

    public QuizRoom createRoom(String roomName, String username, ClientHandler handler) {
        String roomId = "R" + nextRoomId.getAndIncrement();
        QuizRoom room = new QuizRoom(roomId, roomName, questionBank.allQuestions());
        rooms.put(roomId, room);
        if (!room.addPlayer(username, handler)) {
            rooms.remove(roomId);
            return null;
        }
        handler.setRoomId(roomId);
        handler.setReady(false);
        return room;
    }

    public QuizRoom joinRoom(String roomId, String username, ClientHandler handler) {
        QuizRoom room = rooms.get(roomId);
        if (room == null) {
            return null;
        }
        if (!room.addPlayer(username, handler)) {
            return null;
        }
        handler.setRoomId(roomId);
        handler.setReady(false);
        return room;
    }

    public QuizRoom leaveCurrentRoom(String username, ClientHandler handler) {
        String roomId = handler.getRoomId();
        if (roomId == null) {
            return null;
        }
        QuizRoom room = rooms.get(roomId);
        handler.setRoomId(null);
        handler.setReady(false);
        if (room == null) {
            return null;
        }
        room.removePlayer(username);
        if (room.isEmpty()) {
            rooms.remove(roomId);
        }
        return room;
    }

    public QuizRoom getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public List<String> listRooms() {
        List<String> snapshots = new ArrayList<>();
        for (QuizRoom room : rooms.values()) {
            snapshots.add(room.getRoomId() + ":" + room.getRoomName() + ":" + room.getPlayerCount());
        }
        return snapshots;
    }

    public void closeRoomAfterGame(QuizRoom room) {
        String roomId = room.getRoomId();
        rooms.remove(roomId);
        for (ClientHandler handler : room.playerHandlersSnapshot()) {
            handler.setRoomId(null);
            handler.setReady(false);
            handler.send("OK|ROOM_CLOSED|" + roomId);
        }
    }
}
