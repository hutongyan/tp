package seedu.address.model.book.exceptions;

/**
 * Signals that the book is not available to be issued.
 */
public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(String msg) {
        super(msg);
    }
}
