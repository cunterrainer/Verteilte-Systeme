package de.othr.vs.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuizClient {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080/api";

    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public QuizClient(String baseUrl) {
        this.objectMapper = new ObjectMapper();
        this.baseUrl = baseUrl;
    }

    public static void main(String[] args) {
        String baseUrl = parseArgs(args);
        if (baseUrl == null) {
            return;
        }

        QuizClient client = new QuizClient(baseUrl);
        try {
            client.runInteractiveLoop();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }

    private void execute(String command, String[] commandArgs) throws IOException {
        switch (command) {
            case "list":
                listQuestions();
                break;
            case "question":
                showQuestion(commandArgs);
                break;
            case "random":
                showRandomQuestion();
                break;
            case "answer":
                submitAnswer(commandArgs);
                break;
            case "score":
                showScore();
                break;
            case "reset":
                resetAnswers();
                break;
            case "help":
                printHelp();
                break;
            default:
                System.err.println("Unknown command: " + command);
                printHelp();
        }
    }

    private void runInteractiveLoop() throws IOException {
        System.out.println("QuizClient interactive mode");
        System.out.println("Base URL: " + baseUrl);
        System.out.println("Type 'help' for commands, 'exit' to quit.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        while (true) {
            System.out.print("quiz> ");
            String line = reader.readLine();
            if (line == null) {
                System.out.println();
                break;
            }

            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            if ("exit".equalsIgnoreCase(trimmed) || "quit".equalsIgnoreCase(trimmed)) {
                System.out.println("Goodbye.");
                break;
            }

            String[] parts = trimmed.split("\\s+");
            String command = parts[0];
            String[] commandArgs = new String[Math.max(0, parts.length - 1)];
            if (commandArgs.length > 0) {
                System.arraycopy(parts, 1, commandArgs, 0, commandArgs.length);
            }

            try {
                execute(command, commandArgs);
            } catch (Exception e) {
                System.err.println("Client error: " + e.getMessage());
            }
        }
    }

    private void listQuestions() throws IOException {
        SimpleResponse response = get("/questions");
        if (!isSuccess(response.statusCode)) {
            printHttpError(response, "Could not fetch questions.");
            return;
        }

        QuestionView[] questions = readJson(response.body, QuestionView[].class);
        System.out.println("=== Quiz Questions ===");
        if (questions == null || questions.length == 0) {
            System.out.println("No questions available.");
            return;
        }

        for (QuestionView question : questions) {
            printQuestion(question);
            System.out.println();
        }
    }

    private void showQuestion(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: question <id>");
            return;
        }
        int id = parseInt(args[0], "question id");
        if (id < 0) {
            return;
        }

        SimpleResponse response = get("/questions/" + id);
        if (!isSuccess(response.statusCode)) {
            printHttpError(response, "Could not fetch question " + id + ".");
            return;
        }

        QuestionView question = readJson(response.body, QuestionView.class);
        System.out.println("=== Question ===");
        printQuestion(question);
    }

    private void showRandomQuestion() throws IOException {
        SimpleResponse response = get("/questions/random");
        if (!isSuccess(response.statusCode)) {
            printHttpError(response, "Could not fetch random question.");
            return;
        }

        QuestionView question = readJson(response.body, QuestionView.class);
        System.out.println("=== Random Question ===");
        printQuestion(question);
    }

    private void submitAnswer(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: answer <questionId> <selectedAnswer>");
            return;
        }

        int questionId = parseInt(args[0], "questionId");
        if (questionId < 0) {
            return;
        }

        String selectedAnswer = joinFrom(args, 1);
        if (selectedAnswer.isBlank()) {
            System.err.println("selectedAnswer cannot be empty.");
            return;
        }

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("questionId", questionId);
        payload.put("selectedAnswer", selectedAnswer);

        SimpleResponse response = post("/answers", writeJson(payload));
        if (!isSuccess(response.statusCode)) {
            printHttpError(response, "Could not submit answer.");
            return;
        }

        SubmitAnswerResponse result = readJson(response.body, SubmitAnswerResponse.class);
        System.out.println("=== Answer Result ===");
        System.out.println("Question ID : " + result.questionId);
        System.out.println("Result      : " + (result.correct ? "CORRECT" : "WRONG"));
        System.out.println("Message     : " + result.message);
    }

    private void showScore() throws IOException {
        SimpleResponse response = get("/score");
        if (!isSuccess(response.statusCode)) {
            printHttpError(response, "Could not fetch score.");
            return;
        }

        ScoreResponse score = readJson(response.body, ScoreResponse.class);
        System.out.println("=== Current Score ===");
        System.out.println("Answered       : " + score.totalQuestions);
        System.out.println("Correct        : " + score.correctAnswers);
        System.out.println("Score (%)      : " + score.score);
    }

    private void resetAnswers() throws IOException {
        SimpleResponse response = delete("/answers");
        if (!isSuccess(response.statusCode)) {
            printHttpError(response, "Could not reset answers.");
            return;
        }
        System.out.println("All submitted answers were reset.");
    }

    private SimpleResponse get(String path) throws IOException {
        return send("GET", path, null);
    }

    private SimpleResponse post(String path, String body) throws IOException {
        return send("POST", path, body);
    }

    private SimpleResponse delete(String path) throws IOException {
        return send("DELETE", path, null);
    }

    private SimpleResponse send(String method, String path, String requestBody) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl + path).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if (requestBody != null) {
            byte[] bytes = requestBody.getBytes(StandardCharsets.UTF_8);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            connection.getOutputStream().write(bytes);
        }

        int status = connection.getResponseCode();
        String body = readResponseBody(connection, status);
        connection.disconnect();
        return new SimpleResponse(status, body);
    }

    private String readResponseBody(HttpURLConnection connection, int statusCode) throws IOException {
        InputStream stream = statusCode >= 400 ? connection.getErrorStream() : connection.getInputStream();
        if (stream == null) {
            return "";
        }
        byte[] bytes = stream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private <T> T readJson(String body, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(body, clazz);
    }

    private String writeJson(Object payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
    }

    private static boolean isSuccess(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    private static int parseInt(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid " + fieldName + ": " + value);
            return -1;
        }
    }

    private static String joinFrom(String[] parts, int startIndex) {
        StringBuilder value = new StringBuilder();
        for (int i = startIndex; i < parts.length; i++) {
            if (i > startIndex) {
                value.append(' ');
            }
            value.append(parts[i]);
        }
        return value.toString();
    }

    private static void printQuestion(QuestionView question) {
        if (question == null) {
            System.out.println("Question not found.");
            return;
        }

        System.out.println("[" + question.id + "] " + question.text);
        if (question.options != null) {
            for (int i = 0; i < question.options.length; i++) {
                System.out.println("  " + (i + 1) + ". " + question.options[i]);
            }
        }
    }

    private static void printHttpError(SimpleResponse response, String contextMessage) {
        System.err.println(contextMessage);
        System.err.println("HTTP " + response.statusCode);
        if (response.body != null && !response.body.isBlank()) {
            System.err.println(response.body);
        }
    }

    private static String parseArgs(String[] args) {
        String baseUrl = DEFAULT_BASE_URL;
        if (args.length >= 2 && "--base-url".equals(args[0])) {
            baseUrl = args[1];
        }

        return baseUrl;
    }

    private static void printHelp() {
        System.out.println("QuizClient - simple command-based client");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java -cp <jar> de.othr.vs.client.QuizClient [--base-url <url>]");
        System.out.println("  java -cp <jar> de.othr.vs.client.QuizClient [--base-url <url>] <command> [args]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  list");
        System.out.println("  question <id>");
        System.out.println("  random");
        System.out.println("  answer <questionId> <selectedAnswer>");
        System.out.println("  score");
        System.out.println("  reset");
        System.out.println("  help");
        System.out.println("  exit / quit (interactive mode)");
        System.out.println();
        System.out.println("Default base URL: " + DEFAULT_BASE_URL);
    }

    private static class SimpleResponse {
        private final int statusCode;
        private final String body;

        private SimpleResponse(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }
    }

    private static class QuestionView {
        public int id;
        public String text;
        public String[] options;
    }

    private static class SubmitAnswerResponse {
        public int questionId;
        public boolean correct;
        public String message;
    }

    private static class ScoreResponse {
        public int totalQuestions;
        public int correctAnswers;
        public int score;
    }
}
