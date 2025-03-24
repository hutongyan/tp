package seedu.address.model.book.exceptions;

import seedu.address.logic.Messages;
import seedu.address.model.book.Book;
import seedu.address.model.exceptions.AddressBookException;


import static seedu.address.logic.Messages.MESSAGE_ADD_BOOK_FAIL;

/**
 * Signals that the operation will result in duplicate Books (Books are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBookException extends AddressBookException {
    public DuplicateBookException(Book bk) {
        super(String.format(MESSAGE_ADD_BOOK_FAIL, Messages.format(bk)));
    }
}
