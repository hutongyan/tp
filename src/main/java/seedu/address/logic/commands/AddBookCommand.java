package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Adds a book to the address book.
 */
public class AddBookCommand extends Command {
    public static final String COMMAND_WORD = "add_book";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a book to the address book.\n"
            + "Parameters: " + PREFIX_BOOK + "BOOK NAME" + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_BOOK + " Percy Jackson";
    public static final String MESSAGE_ADD_BOOK_SUCCESS = "Added Book: %1$s";
    public static final String MESSAGE_ADD_BOOK_FAIL = "Book: %1$s already exists in book list.";

    private final Book book;

    public AddBookCommand(Book book) {
        this.book = book;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();

        for (Book book : lastShownList) {
            if (this.book.getName().toString().equals(book.getName().toString())) {
                throw new CommandException(String.format(MESSAGE_ADD_BOOK_FAIL, Messages.format(book)));
            }
        }

        model.addBook(book);
        return new CommandResult(String.format(MESSAGE_ADD_BOOK_SUCCESS, Messages.format(book)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddBookCommand)) {
            return false;
        }

        AddBookCommand otherAddCommand = (AddBookCommand) other;
        return book.equals(otherAddCommand.book);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(AddBookCommand.class)
                .add("book", book)
                .toString();
    }
}
