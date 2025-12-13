import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlashcardTest {

    @Test
    void checkAnswer_correctAnswer_returnsTrue() {
        Flashcard c = new Flashcard("addition", "2 + 3 = ?", 5);
        assertTrue(c.checkAnswer("5"));
    }

    @Test
    void checkAnswer_wrongAnswer_returnsFalse() {
        Flashcard c = new Flashcard("addition", "2 + 3 = ?", 5);
        assertFalse(c.checkAnswer("6"));
    }

    @Test
    void checkAnswer_trimsSpaces_stillWorks() {
        Flashcard c = new Flashcard("addition", "2 + 3 = ?", 5);
        assertTrue(c.checkAnswer("   5   "));
    }

    @Test
    void checkAnswer_nonNumber_returnsFalse() {
        Flashcard c = new Flashcard("addition", "2 + 3 = ?", 5);
        assertFalse(c.checkAnswer("five"));
    }

    @Test
    void getters_returnExpectedValues() {
        Flashcard c = new Flashcard("division", "8 ÷ 2 = ?", 4);
        assertEquals("division", c.getCategory());
        assertEquals("8 ÷ 2 = ?", c.getQuestion());
        assertEquals(4, c.getAnswer());
    }
}
