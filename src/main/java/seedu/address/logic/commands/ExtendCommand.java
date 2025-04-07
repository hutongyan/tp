package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookNotBorrowedException;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.book.exceptions.DifferentBorrowerException;
import seedu.address.model.person.Email;


/**
 * Extends the duration for which a book can be borrowed without paying overdue fees
 */
public class ExtendCommand extends Command {

    public static final String COMMAND_WORD = "extend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Extends the duration for which the user can borrow a book without paying overdue fees.\n"
            + "Parameters: "
            + PREFIX_BOOK + "BOOK NAME"
            + PREFIX_EMAIL + "EMAIL"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BOOK + "Percy Jackson"
            + PREFIX_EMAIL + "alexyeoh@example.com";

    public static final String MESSAGE_SUCCESS = "Duration extended for book: %1$s for user: %2$s";
    public static final String MESSAGE_FAILURE = "Failed to extend %s because %s";
    private final BookName bookName;
    private final Email email;

    /**
     * Creates an ExtendCommand to extend the duration for the specified {@code Book} to the specified {@code Person}
     */
    public ExtendCommand(BookName bookName, Email email) {
        requireNonNull(bookName);
        requireNonNull(email);
        this.bookName = bookName;
        this.email = email;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasBook(bookName)) {
            throw new CommandException(String.format(MESSAGE_FAILURE, bookName, "Book not found"));
        }

        if (!model.hasPerson(email)) {
            throw new CommandException(String.format(MESSAGE_FAILURE, bookName, "Person not found"));
        }


        try {
            model.extendBook(bookName, email);
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    bookName, model.getPersonName(email)));
        } catch (BookNotBorrowedException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, bookName, e.getMessage()));
        } catch (DifferentBorrowerException f) {
            throw new CommandException(String.format(MESSAGE_FAILURE, bookName, f.getMessage()));
        } catch (BookUnavailableException g) {
            throw new CommandException(String.format(MESSAGE_FAILURE, bookName, g.getMessage()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ExtendCommand)) {
            return false;
        }
        ExtendCommand otherExtendCommand = (ExtendCommand) other;
        return bookName.equals(otherExtendCommand.bookName)
                && email.equals(otherExtendCommand.email);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("book", bookName)
                .add("person", email)
                .toString();
    }
}
