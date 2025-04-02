package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Lists all books in the library to the user.
 */
public class ListBookCommand extends Command {
    public static final String COMMAND_WORD = "list_books";
    public static final String MESSAGE_SUCCESS = "Listed all books: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all books in the library. ";

    @Override
    public CommandResult execute(Model model) {
        List<Book> books = model.getFilteredBookList();
        StringBuilder bookList = new StringBuilder(MESSAGE_SUCCESS);
        for (Book book : books) {
            bookList.append("\n").append(book.getName().toString());
        }
        return new CommandResult(bookList.toString());
    }

}
