package seedu.address.model.wedding.exceptions;

/**
 * Signals that the operation will result in duplicate Weddings (Weddings are considered duplicates based on
 * the {@code Wedding#isSameWedding(Wedding)} method).
 */
public class DuplicateWeddingException extends RuntimeException {
    public DuplicateWeddingException() {
        super("Operation would result in duplicate weddings");
    }
}
