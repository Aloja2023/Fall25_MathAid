package FlashCardProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class FlashcardApp {
    
    private String userName;

    private final Stack<FlashCard> missedStack = new Stack<>();
    private final Map<String, UserStats> statsByCategory = new HashMap<>();

    private final Scanner scanner = new Scanner(System.in);

    private final List<String> categories = Arrays.asList(
        "Addition", 
        "Subtraction", 
        "Multiplication", 
        "Division"
    );
    public static void main(String[] args) {
        FlashcardApp app = new FlashcardApp();
        app.run();
    }
    private void run() {
        userName = ProgressManager.load(statsByCategory);

        if (userName == null || userName.isEmpty()) {
            System.out.print("Enter your name: ");
            userName = scanner.nextLine().trim();
            if (userName.isEmpty()) {
                userName = "Sudent";
            }
        }
        System.out.println("Welcome, " + userName + "!");
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readint("Enter your choice (1-6):");
            switch (choice) {
                case 1 -> startStudyMode();
                case 2 -> startGameMode();
                case 3 -> reviewMissedFlashcards();
                case 4 -> viewProgress();
                case 5 -> resetProgress();
                case 6 -> {
                    System.out.println("Exiting the application. Goodbye!" + userName + "!");
                    ProgressManager.save(userName, statsByCategory);
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please choose a valid option (1-6).");
            }
        }
    }
    private int readint(String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }
    private void printMainMenu() {
        System.out.println();
        System.out.println("====== Math Flashcards ======");
        System.out.println("Student: " + userName);
        System.out.println("-----------------------------");
        System.out.println("1. Study Mode");
        System.out.println("2. Game Mode");
        System.out.println("3. Review Missed Flashcards");
        System.out.println("4. View Progress");
        System.out.println("5. Reset Progress");
        System.out.println("6. Exit");
        System.out.println("=============================");  
    }

    //Study Mode
    private void startStudyMode() {
        System.out.println("Starting Study Mode...");
        String category = selectCategory();
        int difficulty = selectDifficulty();
        boolean continueStudying = true;
        while (continueStudying) {
            FlashCard flashCard = FlashCardGenerator.generateFlashcard(category, difficulty);
            System.out.println("Question: " + flashCard.getQuestion());
            String userAnswer = readString("Your answer: ");
            if (userAnswer.equalsIgnoreCase(flashCard.getAnswer())) {
                System.out.println("Correct!");
                getUserStats(category).recordResult(true);
            } else {
                System.out.println("Incorrect. The correct answer is: " + flashCard.getAnswer());
                getUserStats(category).recordResult(false);
                missedStack.push(flashCard);
            }
            String cont = readString("Do you want to continue studying? (yes/no): ");
            continueStudying = cont.equalsIgnoreCase("yes") || cont.equalsIgnoreCase("y");
        }
    }
    //Game Mode
    private void startGameMode() {
        System.out.println("Starting Game Mode...");
        String category = selectCategory();
        int difficulty = selectDifficulty();
        int score = 0;
        int rounds = 5;
        for (int i = 0; i < rounds; i++) {
            FlashCard flashCard = FlashCardGenerator.generateFlashcard(category, difficulty);
            System.out.println("Question: " + flashCard.getQuestion());                         
            String userAnswer = readString("Your answer: ");
            if (userAnswer.equalsIgnoreCase(flashCard.getAnswer())) {
                System.out.println("Correct!");
                score++;
                getUserStats(category).recordResult(true);
            } else {
                System.out.println("Incorrect. The correct answer is: " + flashCard.getAnswer());
                getUserStats(category).recordResult(false);
                missedStack.push(flashCard);
            }
        }
        System.out.println("Game Over! Your score: " + score + "/" + rounds);
    }
    //Review Missed Flashcards
    private void reviewMissedFlashcards() {
        if (missedStack.isEmpty()) {
            System.out.println("No missed flashcards to review.");
            return;
        }
        System.out.println("Reviewing Missed Flashcards...");
        while (!missedStack.isEmpty()) {
            FlashCard flashCard = missedStack.pop();
            System.out.println("Question: " + flashCard.getQuestion());
            String userAnswer = readString("Your answer: ");
            if (userAnswer.equalsIgnoreCase(flashCard.getAnswer())) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. The correct answer is: " + flashCard.getAnswer()); 
                missedStack.push(flashCard);
            }
        }
    } 
    //View Progress
    private void viewProgress() {
        if (statsByCategory.isEmpty()) {
            System.out.println("No progress recorded yet.");
            return;
        }
        List<Map.Entry<String, UserStats>> list =
            new ArrayList<>(statsByCategory.entrySet());
        list.sort((a, b) -> Double.compare(
            b.getValue().getAccuracy(), 
            a.getValue().getAccuracy()
        ));
        System.out.println();
        System.out.println("====== Progress Report for " + userName + " ======");
        System.out.printf("%-15s | %s%n", "Category", "Stats");
        System.out.println("--------------------------------"); 
        for (Map.Entry<String, UserStats> entry : list) {
            String category = entry.getKey();
            UserStats stats = entry.getValue();
            System.out.printf("%-15s -> %s%n", category, stats.toString());
        }
        System.out.println("================================");
    }
    //Reset Progress
    private void resetProgress() {
        System.out.println("Are you sure you want to reset all progress? (yes/no): ");
        String ans = scanner.nextLine().trim().toLowerCase();
        if (ans.equals("yes") || ans.equals("y")) {
            statsByCategory.clear();
            missedStack.clear();
            ProgressManager.save(userName, statsByCategory);
            System.out.println("Progress reset for " + userName + " completed.");
        } else {
            System.out.println("Progress reset canceled.");
        }
    }
    //Helper Methods
    private String selectCategory() {
        System.out.println("Select a category:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, categories.get(i));
        }
        int choice = readint("Enter the number of your choice (1-" + categories.size() + "):");
        if (choice < 1 || choice > categories.size()) {
            System.out.println("Invalid choice.");
            return null;    
        }
        return categories.get(choice - 1);
    }
    private int selectDifficulty() {
        System.out.println("Select difficulty level:");
        System.out.println("1. Easy (numbers up to 10)");
        System.out.println("2. Medium (numbers up to 50)");
        System.out.println("3. Hard (numbers up to 100)");
        int choice = readint("Enter the number of your choice (1-3): ");
        if (choice < 1 || choice > 3) {
            System.out.println("Invalid choice, please select 1, 2, or 3.");
            return -1;    
        }
        return choice;
    }
    private UserStats getUserStats(String category) {
        return statsByCategory.computeIfAbsent(category, k -> new UserStats());
    }
}
