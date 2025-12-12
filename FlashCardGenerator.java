import java.util.Random;


public class FlashCardGenerator {

    private static final   Random RAND = new Random();
    /* Difficulty levels */
    public static FlashCard generateFlashcard(String category, int difficulty) {
        int max;
        switch (difficulty) {
            case 1:
                max = 10;
                break;
            case 2:
                max = 50;
                break;
            case 3:
                max = 100;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level");
        }
        switch (category.toLowerCase()) {
            case "addition":
                return generateAdditionFlashcard(max);
            case "subtraction":
                return generateSubtractionFlashcard(max);
            case "multiplication":
                return generateMultiplicationFlashcard(max/2); /*Only small numbers */
            case "division":
                return generateDivisionFlashcard(max/2); /*Only small numbers */
            default:
                throw new IllegalArgumentException("Invalid category"); 
        }

    }
    private static FlashCard generateAdditionFlashcard(int max) {
        int a = RAND.nextInt(max) + 1;
        int b = RAND.nextInt(max) + 1;
        String question = "What is " + a + " + " + b + "?";
        String answer = Integer.toString(a + b);
        return new FlashCard("Addition", question, answer);
    }
    private static FlashCard generateSubtractionFlashcard(int max) {
        int a = RAND.nextInt(max) + 1;
        int b = RAND.nextInt(a) + 1; // Ensure non-negative result
        String question = "What is " + a + " - " + b + "?";
        String answer = Integer.toString(a - b);
        return new FlashCard("Subtraction", question, answer);
    }
    private static FlashCard generateMultiplicationFlashcard(int max) {
        int a = RAND.nextInt(max) + 1;
        int b = RAND.nextInt(max) + 1;
        String question = "What is " + a + " * " + b + "?";
        String answer = Integer.toString(a * b);
        return new FlashCard("Multiplication", question, answer);
    }
    private static FlashCard generateDivisionFlashcard(int max) {
        int b = RAND.nextInt(max) + 1; // Divisor
        int quotient = RAND.nextInt(max) + 1;
        int a = b * quotient; // Dividend
        String question = "What is " + a + " / " + b + "?";
        String answer = Integer.toString(quotient);
        return new FlashCard("Division", question, answer);
    }
}