package seedu.address.model.book.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.exceptions.LibraryException;

import static seedu.address.logic.Messages.MESSAGE_DELETE_BOOK_FAIL;

/**
 * Signals that the operation is unable to find the specified book.
 */
public class BookNotFoundException extends CommandException {
    public BookNotFoundException(BookName bookName) {
        super(String.format(MESSAGE_DELETE_BOOK_FAIL, bookName));
    }
}
