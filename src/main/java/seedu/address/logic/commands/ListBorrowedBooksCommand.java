package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
/**
 * Represents a command to list all books borrowed by a specified user.
 */
public class ListBorrowedBooksCommand extends Command {
    public static final String COMMAND_WORD = "list_borrowed_books";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all books borrowed by selected user.\n"
            + "Parameters: "
            + PREFIX_EMAIL + "EMAIL"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "alexyeoh@example.com";

    public static final String MESSAGE_SUCCESS = "%1$s"
        + "\n"
        + "Listed all borrowed books by: %2$s";
    public static final String MESSAGE_FAILURE = "Failed to list books: ";
    public static final String MESSAGE_NO_BORROWED_BOOKS = "User is currently not borrowing any books.";
    private final Email email;

    /**
     * Constructs a ListBorrowedBooksCommand to list all books borrowed by the specified user.
     *
     * @param email The email of the user whose borrowed books are to be listed. Must not be null.
     */
    public ListBorrowedBooksCommand(Email email) {
        requireNonNull(email);
        this.email = email;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(email)) {
            throw new CommandException(String.format(MESSAGE_FAILURE, "Person not found"));
        }

        String borrowedBooks = model.listBorrowedBooks(email);

        if (borrowedBooks.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_BORROWED_BOOKS));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, borrowedBooks, model.getPersonName(email)));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ListBorrowedBooksCommand)) {
            return false;
        }
        ListBorrowedBooksCommand that = (ListBorrowedBooksCommand) other;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

