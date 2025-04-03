package seedu.address.model.book.exceptions;

import seedu.address.model.exceptions.AddressBookException;

/**
 * Signals that the book is currently borrowed by a different person
 */
public class DifferentBorrowerException extends AddressBookException {
    public DifferentBorrowerException(String message) {
        super(message);
    }
}
