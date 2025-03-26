package seedu.address.model.book.exceptions;

import seedu.address.model.exceptions.AddressBookException;


/**
 * Signals that the book is not available to be issued.
 */
public class BookUnavailableException extends AddressBookException {
    public BookUnavailableException(String msg) {
        super(msg);
    }
}
