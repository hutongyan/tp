package seedu.address.model.book.exceptions;

import seedu.address.model.exceptions.AddressBookException;


/**
 * Signals that the book is not already borrowed.
 */
public class BookNotBorrowedException extends AddressBookException {
    public BookNotBorrowedException(String msg) {
        super(msg);
    }
}
