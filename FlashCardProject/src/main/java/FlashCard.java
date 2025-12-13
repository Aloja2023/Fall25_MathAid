package FlashCardProject.src.main.java;
public class FlashCard {
    private final String category;
    private final String question;
    private final String answer;

    public FlashCard(String category, String question, String answer) {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }
    public String getCategory() {
        return category;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "FlashCard{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}