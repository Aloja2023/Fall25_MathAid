import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FlashCardTest {

    @Test
    void checkAnswer_correctAnswer_returnsTrue() {
    FlashCard c = new FlashCard("addition", "2 + 3 = ?", 5);
        assertTrue(c.checkAnswer("5"));
    }

    @Test
    void checkAnswer_wrongAnswer_returnsFalse() {
    FlashCard c = new FlashCard("addition", "2 + 3 = ?", 5);
        assertFalse(c.checkAnswer("6"));
    }

    @Test
    void checkAnswer_trimsSpaces_stillWorks() {
    FlashCard c = new FlashCard("addition", "2 + 3 = ?", 5);
        assertTrue(c.checkAnswer("   5   "));
    }

    @Test
    void checkAnswer_nonNumber_returnsFalse() {
    FlashCard c = new FlashCard("addition", "2 + 3 = ?", 5);
        assertFalse(c.checkAnswer("five"));
    }

    @Test
    void getters_returnExpectedValues() {
    FlashCard c = new FlashCard("division", "8 ÷ 2 = ?", 4);
        assertEquals("division", c.getCategory());
        assertEquals("8 ÷ 2 = ?", c.getQuestion());
        assertEquals(4, c.getAnswer());
    }
}
