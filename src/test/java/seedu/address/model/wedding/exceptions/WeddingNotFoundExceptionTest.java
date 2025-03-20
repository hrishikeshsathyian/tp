package seedu.address.model.wedding.exceptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeddingNotFoundExceptionTest {

    @Test
    public void constructor_default_createsInstance() {
        WeddingNotFoundException exception = new WeddingNotFoundException();
        assertTrue(exception instanceof WeddingNotFoundException);
    }

    @Test
    public void inheritance_isRuntimeException_true() {
        WeddingNotFoundException exception = new WeddingNotFoundException();
        assertTrue(exception instanceof RuntimeException);
    }
}
