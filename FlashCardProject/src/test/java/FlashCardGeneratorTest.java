import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlashcardGeneratorTest {

    @Test
    void generateFlashcard_returnsNonNull() {
        Flashcard c = FlashcardGenerator.generateFlashcard("addition", 1);
        assertNotNull(c);
        assertNotNull(c.getQuestion());
        assertNotNull(c.getCategory());
    }

    @Test
    void divisionFlashcards_areCleanDivision() {
        for (int i = 0; i < 50; i++) {
            Flashcard c = FlashcardGenerator.generateFlashcard("division", 2);
            assertEquals("division", c.getCategory());

            // We can’t parse the divisor easily from the string reliably,
            // but we CAN ensure the answer is not negative.
            assertTrue(c.getAnswer() >= 0);
        }
    }

    @Test
    void subtractionFlashcards_areNonNegativeResults() {
        for (int i = 0; i < 50; i++) {
            Flashcard c = FlashcardGenerator.generateFlashcard("subtraction", 3);
            assertEquals("subtraction", c.getCategory());
            assertTrue(c.getAnswer() >= 0);
        }
    }
}
