import java.util.Locale;

public class Question {
    private final int id;
    private final String text;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    private final String optionD;
    private final String correctOption;
    private final String explanation;

    public Question(int id, String text, String optionA, String optionB, String optionC, String optionD,
                    String correctOption, String explanation) {
        this.id = id;
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.explanation = explanation;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getExplanation() {
        return explanation;
    }

    public String normalizedCorrectOption() {
        return correctOption.trim().toUpperCase(Locale.ROOT);
    }

    public boolean isValidOption(String option) {
        String normalized = option == null ? "" : option.trim().toUpperCase(Locale.ROOT);
        return "A".equals(normalized) || "B".equals(normalized) || "C".equals(normalized) || "D".equals(normalized);
    }
}
