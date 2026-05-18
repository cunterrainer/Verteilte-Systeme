public interface GameEventPublisher {
    void publishGameStarting(String roomId);

    void publishRoundStart(QuizRoom room, Question question, int roundNumber, int roundSeconds);

    void publishRoundResult(String roomId, QuizRoom.RoundOutcome outcome);

    void publishScoreboard(String roomId, String scoreboard);

    void publishGameOver(String roomId, String message, String scoreboard);

    void publishRoomClosed(String roomId);

    void publishError(String roomId, String message);
}
