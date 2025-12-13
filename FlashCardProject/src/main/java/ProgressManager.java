import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ProgressManager {
    // Text file management for user progress
    private static final String PROGRESS_FILE = "user_progress.txt";

    public static void save(String userName, Map<String, UserStats> statsByCategory) {
        try (PrintWriter out = new PrintWriter(new FileWriter(PROGRESS_FILE))) {
            // Save as CSV-like lines. First line for username uses "USER,username"
            out.println("USER," + userName);

            for (Map.Entry<String, UserStats> entry : statsByCategory.entrySet()) {
                String category = entry.getKey();
                UserStats stats = entry.getValue();
                out.printf("%s,%d,%d%n", category, stats.getAttempts(), stats.getCorrect());
            }
            System.out.println("Progress saved successfully to " + PROGRESS_FILE);
        } catch (IOException e) {
            System.out.print("Error saving progress: " + e.getMessage());
        }
    }

    public static String load(Map<String, UserStats> statsByCategory) {
        File file = new File(PROGRESS_FILE);
        if (!file.exists()) {
            // No saved progress
            return null;
        }
        String userName = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(PROGRESS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 0) continue;
                if (parts[0].equals("USER") && parts.length == 2) {
                    userName = parts[1];
                } else if (parts.length == 3) {
                    String category = parts[0];
                    int attempts = Integer.parseInt(parts[1]);
                    int correct = Integer.parseInt(parts[2]);
                    UserStats stats = new UserStats();
                    stats.addAttempts(attempts);
                    stats.addCorrect(correct);
                    statsByCategory.put(category, stats);
                }
            }
            System.out.println("Progress loaded successfully from " + PROGRESS_FILE);
        } catch (IOException e) {
            System.out.print("Error loading progress: " + e.getMessage());
        }
        return userName;
    }
}

