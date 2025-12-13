import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressManagerTest {

    @TempDir
    Path tempDir;

    private String originalUserDir;

    @BeforeEach
    void setUp() {
        originalUserDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());
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
