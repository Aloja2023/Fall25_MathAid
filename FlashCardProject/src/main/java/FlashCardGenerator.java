import java.util.Random;

public class FlashCardGenerator {

    private static final Random RAND = new Random();

    public static FlashCard generateFlashcard(String category, int difficulty) {
        int max;
        switch (difficulty) {
            case 1 -> max = 10;
            case 2 -> max = 50;
            case 3 -> max = 100;
            default -> throw new IllegalArgumentException("Invalid difficulty level");
        }

        switch (category.toLowerCase()) {
            case "addition" -> {
                return generateAdditionFlashcard(max);
            }
            case "subtraction" -> {
                return generateSubtractionFlashcard(max);
            }
            case "multiplication" -> {
                return generateMultiplicationFlashcard(max / 2);
            }
            case "division" -> {
                return generateDivisionFlashcard(max / 2);
            }
            default -> throw new IllegalArgumentException("Invalid category");
        }
    }

    private static FlashCard generateAdditionFlashcard(int max) {
        int a = RAND.nextInt(max) + 1;
        int b = RAND.nextInt(max) + 1;
        String question = "What is " + a + " + " + b + "?";
        int answer = a + b;
        return new FlashCard("addition", question, answer);
    }

    private static FlashCard generateSubtractionFlashcard(int max) {
        int a = RAND.nextInt(max) + 1;
        int b = RAND.nextInt(a) + 1; // ensure non-negative result
        String question = "What is " + a + " - " + b + "?";
        int answer = a - b;
        return new FlashCard("subtraction", question, answer);
    }

    private static FlashCard generateMultiplicationFlashcard(int max) {
        int a = RAND.nextInt(max) + 1;
        int b = RAND.nextInt(max) + 1;
        String question = "What is " + a + " * " + b + "?";
        int answer = a * b;
        return new FlashCard("multiplication", question, answer);
    }

    private static FlashCard generateDivisionFlashcard(int max) {
        int b = RAND.nextInt(max) + 1; // divisor
        int quotient = RAND.nextInt(max) + 1;
        int a = b * quotient; // dividend
        String question = "What is " + a + " / " + b + "?";
        int answer = quotient;
        return new FlashCard("division", question, answer);
    }
}
