package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_ADD_BOOK_FAIL;
import static seedu.address.logic.Messages.MESSAGE_ADD_BOOK_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;

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

    private final Book book;

    /**
     * Creates an AddBookCommand to add the specified {@code Book}
     */
    public AddBookCommand(Book book) {
        requireNonNull(book);
        this.book = book;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasBook(book)) {
            throw new CommandException(String.format(MESSAGE_ADD_BOOK_FAIL, Messages.format(book)));
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
        return new ToStringBuilder(this)
                .add("book", book)
                .toString();
    }
}
