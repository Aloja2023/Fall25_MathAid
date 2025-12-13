public class FlashCard {
    private final String category;
    private final String question;
    private final int answer;

    public FlashCard(String category, String question, int answer) {
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

    public int getAnswer() {
        return answer;
    }

    public boolean checkAnswer(String userInput) {
        if (userInput == null) return false;
        try {
            int parsed = Integer.parseInt(userInput.trim());
            return parsed == answer;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "FlashCard{" + "question='" + question + '\'' + ", answer=" + answer + '}';
    }
}