public class RoomGameLoop implements Runnable {
    private final QuizRoom room;
    private final QuizServerState state;
    private final int roundSeconds;
    private final GameEventPublisher publisher;

    public RoomGameLoop(QuizRoom room, QuizServerState state, int roundSeconds, GameEventPublisher publisher) {
        this.room = room;
        this.state = state;
        this.roundSeconds = roundSeconds;
        this.publisher = publisher;
    }

    @Override
    public void run() {
        try {
            while (room.isGameStarted()) {
                if (room.getPlayerCount() < 2) {
                    publisher.publishGameOver(room.getRoomId(), "Not enough players to continue.",
                            room.formatScoreboard());
                    room.abortGame();
                    publisher.publishRoomClosed(room.getRoomId());
                    state.closeRoomAfterGame(room);
                    return;
                }

                Question question = room.openCurrentRound();
                if (question == null) {
                    room.abortGame();
                    publisher.publishRoomClosed(room.getRoomId());
                    state.closeRoomAfterGame(room);
                    return;
                }

                publisher.publishRoundStart(room, question, room.getCurrentRoundNumber(), roundSeconds);

                long deadline = System.currentTimeMillis() + roundSeconds * 1000L;
                while (System.currentTimeMillis() < deadline) {
                    if (!room.isGameStarted()) {
                        return;
                    }
                    if (room.getPlayerCount() < 2 || room.allPlayersAnswered()) {
                        break;
                    }
                    Thread.sleep(200);
                }

                QuizRoom.RoundOutcome outcome = room.closeRoundAndScore();
                if (outcome != null) {
                    publisher.publishRoundResult(room.getRoomId(), outcome);
                    publisher.publishScoreboard(room.getRoomId(), outcome.getScoreboard());
                }

                if (!room.moveToNextQuestion()) {
                    publisher.publishGameOver(room.getRoomId(), "Game finished.", room.formatScoreboard());
                    room.abortGame();
                    publisher.publishRoomClosed(room.getRoomId());
                    state.closeRoomAfterGame(room);
                    return;
                }
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            publisher.publishError(room.getRoomId(), "GAME_LOOP_INTERRUPTED");
            room.abortGame();
            publisher.publishRoomClosed(room.getRoomId());
            state.closeRoomAfterGame(room);
        }
    }
}
