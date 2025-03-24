package seedu.address.model.book.exceptions;

import seedu.address.model.book.BookName;
import seedu.address.model.exceptions.AddressBookException;

import static seedu.address.logic.Messages.MESSAGE_DELETE_BOOK_FAIL;

/**
 * Signals that the operation is unable to find the specified book.
 */
public class BookNotFoundException extends AddressBookException {
    public BookNotFoundException(BookName bookName) {
        super(String.format(MESSAGE_DELETE_BOOK_FAIL, bookName));
    }
}
