package de.othr.vs.quizarena.net;

import de.othr.vs.quizarena.core.QuizRoom;
import de.othr.vs.quizarena.core.QuizServerState;
import de.othr.vs.quizarena.model.Question;

public class RoomGameLoop implements Runnable {
    private final QuizRoom room;
    private final QuizServerState state;
    private final int roundSeconds;

    public RoomGameLoop(QuizRoom room, QuizServerState state, int roundSeconds) {
        this.room = room;
        this.state = state;
        this.roundSeconds = roundSeconds;
    }

    @Override
    public void run() {
        try {
            while (room.isGameStarted()) {
                if (room.getPlayerCount() < 2) {
                    room.broadcast("GAME_OVER: Not enough players to continue.");
                    room.abortGame();
                    state.closeRoomAfterGame(room);
                    return;
                }

                Question question = room.openCurrentRound();
                if (question == null) {
                    room.abortGame();
                    state.closeRoomAfterGame(room);
                    return;
                }

                room.broadcast(
                        "ROUND_START: " + room.getCurrentRoundNumber() + " | Question: " + question.getText()
                                + " | A=" + question.getOptionA()
                                + " | B=" + question.getOptionB() + " | C=" + question.getOptionC()
                                + " | D=" + question.getOptionD() + " | Time: " + roundSeconds + "s");

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
                    room.broadcast("ROUND_RESULT: " + outcome.getCorrectOption() + " | First Correct: "
                            + outcome.getFirstCorrectUser() + " | Explanation: " + outcome.getExplanation());
                    room.broadcast("SCOREBOARD: " + outcome.getScoreboard());
                }

                if (!room.moveToNextQuestion()) {
                    room.broadcast("GAME_OVER: " + room.formatScoreboard());
                    room.abortGame();
                    state.closeRoomAfterGame(room);
                    return;
                }
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            room.broadcast("ERR: GAME_LOOP_INTERRUPTED");
            room.abortGame();
            state.closeRoomAfterGame(room);
        }
    }
}
