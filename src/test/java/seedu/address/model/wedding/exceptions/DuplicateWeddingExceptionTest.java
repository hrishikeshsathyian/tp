package seedu.address.model.wedding.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DuplicateWeddingExceptionTest {

    @Test
    public void constructor_defaultMessage_messageCorrect() {
        DuplicateWeddingException exception = new DuplicateWeddingException();
        assertEquals("Operation would result in duplicate weddings", exception.getMessage());
    }

    @Test
    public void inheritance_isRuntimeException_true() {
        DuplicateWeddingException exception = new DuplicateWeddingException();
        assertTrue(exception instanceof RuntimeException);
    }
}
