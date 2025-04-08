package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.person.Email;
/**
 * Issues a book to a user.
 */
public class IssueCommand extends Command {

    public static final String COMMAND_WORD = "issue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Issues a book to a user.\n"
            + "Parameters: "
            + PREFIX_BOOK + "BOOK NAME"
            + PREFIX_EMAIL + "EMAIL"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BOOK + "Percy Jackson" + " "
            + PREFIX_EMAIL + "alexyeoh@example.com";

    public static final String MESSAGE_SUCCESS = "Book: %1$s issued to user: %2$s";
    public static final String MESSAGE_FAILURE = "Failed to issue %s because %s";
    private final BookName bookName;
    private final Email email;
    private final LocalDate localDate = LocalDate.now();
    /**
     * Creates an IssueCommand to issue the specified {@code Book} to the specified {@code Person}
     */
    public IssueCommand(BookName bookName, Email email) {
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
            model.issueBook(bookName, email, localDate);
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    bookName, model.getPersonName(email)));
        } catch (BookUnavailableException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, bookName, e.getMessage()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IssueCommand)) {
            return false;
        }
        IssueCommand otherIssueCommand = (IssueCommand) other;
        return bookName.equals(otherIssueCommand.bookName)
                && email.equals(otherIssueCommand.email)
                && localDate.equals(otherIssueCommand.localDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("book", bookName)
                .add("person", email)
                .add("localDate", localDate)
                .toString();
    }
}
