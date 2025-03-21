package seedu.address.model.book.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.LibraryException;

/**
 * Signals that the book is not available to be issued.
 */
public class BookUnavailableException extends CommandException {
    public BookUnavailableException(String msg) {
        super(msg);
    }
}
