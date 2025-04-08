package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookUnavailableException;

/**
 * Marks a borrowed book as returned and calculates overdue fines.
 */
public class ReturnCommand extends Command {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_SUCCESS = "Marked %s as returned. Overdue Fines: S$%d";
    public static final String MESSAGE_FAILURE = "Failed to return %s because %s";
    private static final String STATUS_AVAILABLE = "Available";
    private static final String HUMAN_READABLE_AVAILABLE_ERROR =
            "the book is already marked as available.";

    private final BookName bookName;
    private final LocalDate returnDate;

    /**
     * Creates a ReturnCommand to mark the specified {@code Book} as returned.
     */
    public ReturnCommand(BookName bookName, LocalDate returnDate) {
        this.bookName = bookName;
        this.returnDate = returnDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasBook(bookName)) {
            throw new CommandException(String.format(MESSAGE_FAILURE, bookName, "Book not found"));
        }

        try {
            for (var b : model.getAddressBook().getBookList()) {
                if (b.getName().equals(bookName)) {
                    var issueDate = b.getStatus().getIssueDate();
                    if (issueDate != null && returnDate.isBefore(issueDate)) {
                        throw new CommandException(String.format(
                                MESSAGE_FAILURE, bookName, "return date is before issue date"));
                    }
                }
            }
            int fine = model.returnBook(bookName, returnDate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, bookName, fine));
        } catch (BookUnavailableException e) {
            if (e.getMessage().equals(STATUS_AVAILABLE)) {
                throw new CommandException(String.format(MESSAGE_FAILURE, bookName, HUMAN_READABLE_AVAILABLE_ERROR));
            } else {
                throw new CommandException(String.format(MESSAGE_FAILURE, bookName, e.getMessage()));
            }
        }
    }
}
