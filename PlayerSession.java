package de.othr.vs.quizarena.model;

public class PlayerSession {
    private final String username;
    private volatile String roomId;
    private volatile boolean ready;

    public PlayerSession(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
