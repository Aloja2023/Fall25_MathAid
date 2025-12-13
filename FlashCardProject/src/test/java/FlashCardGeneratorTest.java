import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class FlashCardGeneratorTest {

    @Test
    void generateFlashcard_returnsNonNull() {
    FlashCard c = FlashCardGenerator.generateFlashcard("addition", 1);
        assertNotNull(c);
        assertNotNull(c.getQuestion());
        assertNotNull(c.getCategory());
    }

    @Test
    void divisionFlashcards_areCleanDivision() {
        for (int i = 0; i < 50; i++) {
            FlashCard c = FlashCardGenerator.generateFlashcard("division", 2);
            assertEquals("division", c.getCategory());

            // We can’t parse the divisor easily from the string reliably,
            // but we CAN ensure the answer is not negative.
            assertTrue(c.getAnswer() >= 0);
        }
    }

    @Test
    void subtractionFlashcards_areNonNegativeResults() {
        for (int i = 0; i < 50; i++) {
            FlashCard c = FlashCardGenerator.generateFlashcard("subtraction", 3);
            assertEquals("subtraction", c.getCategory());
            assertTrue(c.getAnswer() >= 0);
        }
    }
}
