import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserStatsTest {

    @Test
    void newStats_hasZeroAttemptsAndCorrect() {
        UserStats s = new UserStats();
        assertEquals(0, s.getAttempts());
        assertEquals(0, s.getCorrect());
        assertEquals(0.0, s.getAccuracy(), 0.0001);
    }

    @Test
    void recordResult_incrementsAttemptsAlways() {
        UserStats s = new UserStats();
        s.recordResult(true);
        s.recordResult(false);
        s.recordResult(false);

        assertEquals(3, s.getAttempts());
        assertEquals(1, s.getCorrect());
    }

    @Test
    void accuracy_isCorrectPercentage() {
        UserStats s = new UserStats();
        // 2 correct out of 5 attempts => 40%
        s.recordResult(true);
        s.recordResult(true);
        s.recordResult(false);
        s.recordResult(false);
        s.recordResult(false);

        assertEquals(5, s.getAttempts());
        assertEquals(2, s.getCorrect());
        assertEquals(40.0, s.getAccuracy(), 0.0001);
    }

    @Test
    void addAttemptsAndAddCorrect_accumulateProperly() {
        UserStats s = new UserStats();
        s.addAttempts(10);
        s.addCorrect(7);

        assertEquals(10, s.getAttempts());
        assertEquals(7, s.getCorrect());
        assertEquals(70.0, s.getAccuracy(), 0.0001);
    }
}
