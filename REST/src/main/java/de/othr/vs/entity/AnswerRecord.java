package de.othr.vs.entity;

public class AnswerRecord {
    private int questionId;
    private String selectedAnswer;
    private boolean correct;

    public AnswerRecord() {
    }

    public AnswerRecord(int questionId, String selectedAnswer, boolean correct) {
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
        this.correct = correct;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
