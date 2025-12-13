import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ProgressManagerTest {

    @TempDir
    Path tempDir;

    private String originalUserDir;

    @BeforeEach
    void setUp() throws IOException {
        originalUserDir = System.getProperty("user.dir");
        // Create a fresh subdirectory for each test so files don't leak between methods
        Path perTest = Files.createTempDirectory(tempDir, "testRun");
        System.setProperty("user.dir", perTest.toString());
        // Ensure no leftover progress file exists before each test (both per-test dir and project root)
        Files.deleteIfExists(perTest.resolve("user_progress.txt"));
        if (originalUserDir != null) {
            Files.deleteIfExists(Path.of(originalUserDir).resolve("user_progress.txt"));
        }
    }

    @AfterEach
    void tearDown() {
        System.setProperty("user.dir", originalUserDir);
    }

    @Test
    void saveAndLoad_persistsUserNameAndStats() {
        Map<String, UserStats> stats = new HashMap<>();

        UserStats add = new UserStats();
        add.addAttempts(10);
        add.addCorrect(8);
        stats.put("addition", add);

        UserStats mult = new UserStats();
        mult.addAttempts(5);
        mult.addCorrect(3);
        stats.put("multiplication", mult);

        String nameSaved = "Bayron";

        // Save
        ProgressManager.save(nameSaved, stats);

        // Load into a fresh map
        Map<String, UserStats> loaded = new HashMap<>();
        String nameLoaded = ProgressManager.load(loaded);

        assertEquals(nameSaved, nameLoaded);
        assertTrue(loaded.containsKey("addition"));
        assertTrue(loaded.containsKey("multiplication"));

        assertEquals(10, loaded.get("addition").getAttempts());
        assertEquals(8, loaded.get("addition").getCorrect());

        assertEquals(5, loaded.get("multiplication").getAttempts());
        assertEquals(3, loaded.get("multiplication").getCorrect());
    }

    @Test
    void load_whenNoFile_returnsNullAndLeavesMapEmpty() {
        Map<String, UserStats> loaded = new HashMap<>();
        String nameLoaded = ProgressManager.load(loaded);

        assertNull(nameLoaded);
        assertTrue(loaded.isEmpty());
    }

    @Test
    void save_withEmptyStats_stillSavesUserName() {
        Map<String, UserStats> stats = new HashMap<>();
        ProgressManager.save("Student", stats);

        Map<String, UserStats> loaded = new HashMap<>();
        String nameLoaded = ProgressManager.load(loaded);

        assertEquals("Student", nameLoaded);
        assertTrue(loaded.isEmpty());
    }
}
