package de.othr.vs.quizarena.core;

import de.othr.vs.quizarena.model.Question;
import de.othr.vs.quizarena.net.ClientHandler;

import java.util.*;

public class QuizRoom {
    public enum SubmitStatus {
        ACCEPTED,
        INVALID_STATE,
        INVALID_OPTION,
        ALREADY_ANSWERED,
        NOT_IN_ROOM
    }

    public static class RoundOutcome {
        private final String correctOption;
        private final String firstCorrectUser;
        private final String explanation;
        private final String scoreboard;

        public RoundOutcome(String correctOption, String firstCorrectUser, String explanation, String scoreboard) {
            this.correctOption = correctOption;
            this.firstCorrectUser = firstCorrectUser;
            this.explanation = explanation;
            this.scoreboard = scoreboard;
        }

        public String getCorrectOption() {
            return correctOption;
        }

        public String getFirstCorrectUser() {
            return firstCorrectUser;
        }

        public String getExplanation() {
            return explanation;
        }

        public String getScoreboard() {
            return scoreboard;
        }
    }

    private static class AnswerSubmission {
        private final String username;
        private final String option;

        private AnswerSubmission(String username, String option) {
            this.username = username;
            this.option = option;
        }
    }

    private final String roomId;
    private final String roomName;
    private final List<Question> questions;
    private final LinkedHashMap<String, ClientHandler> players = new LinkedHashMap<>();
    private final Map<String, Integer> scores = new HashMap<>();
    private final Set<String> readyUsers = new HashSet<>();

    private final List<AnswerSubmission> currentSubmissions = new ArrayList<>();
    private int currentQuestionIndex = -1;
    private boolean gameStarted;
    private boolean roundOpen;

    public QuizRoom(String roomId, String roomName, List<Question> questions) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.questions = questions;
    }

    public synchronized String getRoomId() {
        return roomId;
    }

    public synchronized String getRoomName() {
        return roomName;
    }

    public synchronized boolean addPlayer(String username, ClientHandler handler) {
        if (gameStarted || players.containsKey(username)) {
            return false;
        }
        players.put(username, handler);
        readyUsers.remove(username);
        scores.putIfAbsent(username, 0);
        return true;
    }

    public synchronized void removePlayer(String username) {
        players.remove(username);
        readyUsers.remove(username);
        currentSubmissions.removeIf(s -> s.username.equals(username));
        if (players.isEmpty()) {
            gameStarted = false;
            roundOpen = false;
            currentQuestionIndex = -1;
        }
    }

    public synchronized boolean markReady(String username) {
        if (!players.containsKey(username) || gameStarted) {
            return false;
        }
        readyUsers.add(username);
        return true;
    }

    public synchronized int getPlayerCount() {
        return players.size();
    }

    public synchronized int getReadyCount() {
        return readyUsers.size();
    }

    public synchronized boolean canStart(int minPlayers) {
        return !gameStarted && players.size() >= minPlayers && !players.isEmpty() && readyUsers.size() == players.size();
    }

    public synchronized boolean startGame() {
        if (!canStart(2)) {
            return false;
        }
        gameStarted = true;
        roundOpen = false;
        currentSubmissions.clear();
        currentQuestionIndex = 0;
        return true;
    }

    public synchronized boolean isGameStarted() {
        return gameStarted;
    }

    public synchronized boolean isRoundOpen() {
        return roundOpen;
    }

    public synchronized Question openCurrentRound() {
        if (!gameStarted || currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            return null;
        }
        currentSubmissions.clear();
        roundOpen = true;
        return questions.get(currentQuestionIndex);
    }

    public synchronized int getCurrentRoundNumber() {
        return currentQuestionIndex + 1;
    }

    public synchronized SubmitStatus submitAnswer(String username, String option) {
        if (!players.containsKey(username)) {
            return SubmitStatus.NOT_IN_ROOM;
        }
        if (!roundOpen) {
            return SubmitStatus.INVALID_STATE;
        }
        String normalized = option == null ? "" : option.trim().toUpperCase(Locale.ROOT);
        if (!"A".equals(normalized) && !"B".equals(normalized) && !"C".equals(normalized) && !"D".equals(normalized)) {
            return SubmitStatus.INVALID_OPTION;
        }
        for (AnswerSubmission submission : currentSubmissions) {
            if (submission.username.equals(username)) {
                return SubmitStatus.ALREADY_ANSWERED;
            }
        }
        currentSubmissions.add(new AnswerSubmission(username, normalized));
        return SubmitStatus.ACCEPTED;
    }

    public synchronized boolean allPlayersAnswered() {
        return !players.isEmpty() && currentSubmissions.size() >= players.size();
    }

    public synchronized RoundOutcome closeRoundAndScore() {
        if (!roundOpen || currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            return null;
        }
        roundOpen = false;
        Question question = questions.get(currentQuestionIndex);
        String correctOption = question.normalizedCorrectOption();
        String firstCorrectUser = "NONE";
        int correctCount = 0;

        for (AnswerSubmission submission : currentSubmissions) {
            if (!correctOption.equals(submission.option)) {
                continue;
            }
            correctCount++;
            int bonus = correctCount == 1 ? 5 : correctCount == 2 ? 2 : 0;
            scores.put(submission.username, scores.getOrDefault(submission.username, 0) + 10 + bonus);
            if ("NONE".equals(firstCorrectUser)) {
                firstCorrectUser = submission.username;
            }
        }

        String explanation = "NONE".equals(firstCorrectUser)
                ? "Nobody answered correctly."
                : "Correct answers scored with speed bonus.";

        return new RoundOutcome(correctOption, firstCorrectUser, explanation, formatScoreboard());
    }

    public synchronized boolean moveToNextQuestion() {
        if (!gameStarted) {
            return false;
        }
        int nextIndex = currentQuestionIndex + 1;
        if (nextIndex >= questions.size()) {
            gameStarted = false;
            roundOpen = false;
            readyUsers.clear();
            currentSubmissions.clear();
            return false;
        }
        currentQuestionIndex = nextIndex;
        return true;
    }

    public synchronized void abortGame() {
        gameStarted = false;
        roundOpen = false;
        currentSubmissions.clear();
        readyUsers.clear();
    }

    public synchronized boolean isEmpty() {
        return players.isEmpty();
    }

    public synchronized String formatScoreboard() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(scores.entrySet());
        entries.sort(Comparator
                .comparing(Map.Entry<String, Integer>::getValue).reversed()
                .thenComparing(Map.Entry::getKey));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<String, Integer> entry = entries.get(i);
            if (i > 0) {
                sb.append(",");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    public synchronized void broadcast(String line) {
        for (ClientHandler handler : players.values()) {
            handler.send(line);
        }
    }

    public synchronized List<ClientHandler> playerHandlersSnapshot() {
        return new ArrayList<>(players.values());
    }

    public synchronized boolean hasPlayer(String username) {
        return players.containsKey(username);
    }
}
