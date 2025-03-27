package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
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
            + PREFIX_BOOK + "Percy Jackson"
            + PREFIX_EMAIL + "alexyeoh@example.com";

    public static final String MESSAGE_SUCCESS = "Book issued to user: %1$s";
    public static final String MESSAGE_BOOK_NOT_FOUND = "This book does not exist in the address book";
    public static final String MESSAGE_USER_NOT_FOUND = "This user does not exist in the address book";
    public static final String MESSAGE_BOOK_ALREADY_ISSUED = "This book is already issued to a user";

    private final BookName bookName;
    private final Email email;
    private Book book;
    private Person person;
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

        if (model.getPersonViaEmail(email) == null) {
            throw new CommandException(MESSAGE_USER_NOT_FOUND);
        }

        if (model.getBookViaBookName(bookName) == null) {
            throw new CommandException(MESSAGE_BOOK_NOT_FOUND);
        }

        book = model.getBookViaBookName(bookName);
        person = model.getPersonViaEmail(email);

        if (book.isIssued()) {
            throw new CommandException(MESSAGE_BOOK_ALREADY_ISSUED);
        }

        book.issueBook(localDate, person);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(person)));
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
        return book.equals(otherIssueCommand.book)
                && person.equals(otherIssueCommand.person)
                && localDate.equals(otherIssueCommand.localDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("book", book)
                .add("person", person)
                .add("localDate", localDate)
                .toString();
    }
}
