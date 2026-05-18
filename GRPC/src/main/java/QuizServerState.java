import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class QuizServerState {
    public static class RoomSnapshot {
        private final String roomId;
        private final String roomName;
        private final int playerCount;

        public RoomSnapshot(String roomId, String roomName, int playerCount) {
            this.roomId = roomId;
            this.roomName = roomName;
            this.playerCount = playerCount;
        }

        public String getRoomId() {
            return roomId;
        }

        public String getRoomName() {
            return roomName;
        }

        public int getPlayerCount() {
            return playerCount;
        }
    }

    private final Map<String, PlayerSession> users = new ConcurrentHashMap<>();
    private final Map<String, QuizRoom> rooms = new ConcurrentHashMap<>();
    private final AtomicInteger nextRoomId = new AtomicInteger(1);
    private final QuestionBank questionBank;

    public QuizServerState(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public boolean registerUser(String username) {
        return users.putIfAbsent(username, new PlayerSession(username)) == null;
    }

    public void unregisterUser(String username) {
        if (username == null) {
            return;
        }
        users.remove(username);
    }

    public boolean isRegistered(String username) {
        return users.containsKey(username);
    }

    public PlayerSession getSession(String username) {
        return users.get(username);
    }

    public QuizRoom createRoom(String roomName, String username) {
        PlayerSession session = users.get(username);
        if (session == null) {
            return null;
        }
        String roomId = "R" + nextRoomId.getAndIncrement();
        QuizRoom room = new QuizRoom(roomId, roomName, questionBank.allQuestions());
        rooms.put(roomId, room);
        if (!room.addPlayer(username)) {
            rooms.remove(roomId);
            return null;
        }
        session.setRoomId(roomId);
        session.setReady(false);
        return room;
    }

    public QuizRoom joinRoom(String roomId, String username) {
        PlayerSession session = users.get(username);
        if (session == null) {
            return null;
        }
        QuizRoom room = rooms.get(roomId);
        if (room == null) {
            return null;
        }
        if (!room.addPlayer(username)) {
            return null;
        }
        session.setRoomId(roomId);
        session.setReady(false);
        return room;
    }

    public QuizRoom leaveCurrentRoom(String username) {
        PlayerSession session = users.get(username);
        if (session == null) {
            return null;
        }
        String roomId = session.getRoomId();
        if (roomId == null) {
            return null;
        }
        QuizRoom room = rooms.get(roomId);
        session.setRoomId(null);
        session.setReady(false);
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

    public List<RoomSnapshot> listRooms() {
        List<RoomSnapshot> snapshots = new ArrayList<>();
        for (QuizRoom room : rooms.values()) {
            snapshots.add(new RoomSnapshot(room.getRoomId(), room.getRoomName(), room.getPlayerCount()));
        }
        return snapshots;
    }

    public void closeRoomAfterGame(QuizRoom room) {
        String roomId = room.getRoomId();
        rooms.remove(roomId);
        for (String username : room.playerUsernamesSnapshot()) {
            PlayerSession session = users.get(username);
            if (session == null) {
                continue;
            }
            session.setRoomId(null);
            session.setReady(false);
        }
    }
}
