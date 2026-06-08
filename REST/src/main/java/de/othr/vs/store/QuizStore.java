package de.othr.vs.store;

import de.othr.vs.entity.AnswerRecord;
import de.othr.vs.entity.QuestionView;
import de.othr.vs.entity.QuizQuestion;
import de.othr.vs.entity.ScoreResponse;
import de.othr.vs.entity.SubmitAnswerResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class QuizStore {
    private static final QuizStore INSTANCE = new QuizStore();

    private final Map<Integer, QuizQuestion> questions = new ConcurrentHashMap<>();
    private final Map<Integer, AnswerRecord> answers = new ConcurrentHashMap<>();

    private QuizStore() {
        seedQuestions();
    }

    public static QuizStore getInstance() {
        return INSTANCE;
    }

    public List<QuestionView> getAllQuestions() {
        List<QuestionView> result = new ArrayList<>();
        for (QuizQuestion question : questions.values()) {
            result.add(toView(question));
        }
        result.sort((q1, q2) -> Integer.compare(q1.getId(), q2.getId()));
        return result;
    }

    public QuestionView getQuestionById(int id) {
        QuizQuestion question = questions.get(id);
        if (question == null) {
            return null;
        }
        return toView(question);
    }

    public QuestionView getRandomQuestion() {
        List<QuizQuestion> allQuestions = new ArrayList<>(questions.values());
        if (allQuestions.isEmpty()) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(allQuestions.size());
        return toView(allQuestions.get(randomIndex));
    }

    public SubmitAnswerResponse submitAnswer(int questionId, String selectedAnswer) {
        QuizQuestion question = questions.get(questionId);
        if (question == null) {
            return null;
        }

        boolean correct = question.getCorrectAnswer().equals(selectedAnswer);
        answers.put(questionId, new AnswerRecord(questionId, selectedAnswer, correct));

        String message = correct ? "Correct answer." : "Wrong answer.";
        return new SubmitAnswerResponse(questionId, correct, message);
    }

    public ScoreResponse getScore() {
        int answeredCount = answers.size();
        int correctCount = 0;
        for (AnswerRecord record : answers.values()) {
            if (record.isCorrect()) {
                correctCount++;
            }
        }

        int score = answeredCount == 0 ? 0 : (correctCount * 100) / answeredCount;
        return new ScoreResponse(answeredCount, correctCount, score);
    }

    public void resetAnswers() {
        answers.clear();
    }

    private QuestionView toView(QuizQuestion question) {
        return new QuestionView(question.getId(), question.getText(), question.getOptions());
    }

    private void seedQuestions() {
        questions.put(1, new QuizQuestion(
                1,
                "Which HTTP method is typically used to create a resource?",
                List.of("GET", "POST", "DELETE", "OPTIONS"),
                "POST"
        ));
        questions.put(2, new QuizQuestion(
                2,
                "What does REST primarily model?",
                List.of("Threads", "Resources", "Databases", "Sockets"),
                "Resources"
        ));
        questions.put(3, new QuizQuestion(
                3,
                "Which status code usually means resource not found?",
                List.of("200", "201", "404", "500"),
                "404"
        ));
        questions.put(4, new QuizQuestion(
                4,
                "What media type is commonly used for REST payloads?",
                List.of("application/xml", "text/plain", "application/json", "image/png"),
                "application/json"
        ));
        questions.put(5, new QuizQuestion(
                5,
                "Which method is idempotent by definition?",
                List.of("POST", "PATCH", "PUT", "CONNECT"),
                "PUT"
        ));
    }
}
