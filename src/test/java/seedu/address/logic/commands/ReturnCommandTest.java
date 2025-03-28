package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Membership;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ReturnCommand.
 */
public class ReturnCommandTest {

    private Model model;
    private Book borrowedBook;
    private Book availableBook;
    private Person borrower;

    @BeforeEach
    public void setUp() throws BookUnavailableException {
        model = new ModelManager();

        borrower = new Person(
                new Name("Test User"),
                new Phone("99999999"),
                new Email("test@example.com"),
                new Address("Somewhere"),
                Membership.ACTIVE,
                new HashSet<>()
        );

        borrowedBook = new Book(new BookName("Borrowed Book"), new HashSet<>());
        borrowedBook.getStatus().issueBook(LocalDate.of(2025, 2, 1), borrower);
        availableBook = new Book(new BookName("Available Book"), new HashSet<>());

        model.addBook(borrowedBook);
        model.addBook(availableBook);
    }

    @Test
    public void execute_returnBorrowedBook_successWithFine() throws CommandException {
        ReturnCommand cmd = new ReturnCommand(new BookName("Borrowed Book"), LocalDate.of(2025, 2, 20));
        CommandResult result = cmd.execute(model);

        assertEquals("Marked Borrowed Book as returned. Overdue Fines: 5", result.getFeedbackToUser());
    }

    @Test
    public void execute_returnAvailableBook_failure() {
        ReturnCommand cmd = new ReturnCommand(new BookName("Available Book"), LocalDate.of(2025, 2, 20));

        CommandException thrown = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Failed to return Available Book because the book is already marked as available.",
                                thrown.getMessage());
    }

    @Test
    public void execute_bookNotFound_failure() {
        ReturnCommand cmd = new ReturnCommand(new BookName("Not Exist Book"), LocalDate.of(2025, 2, 20));

        CommandException thrown = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Failed to return Not Exist Book because Book not found", thrown.getMessage());
    }

    @Test
    public void execute_returnThrowsUnexpectedBookUnavailableException_fallbackMessage() {
        Book badBook = new Book(new BookName("Bad Book"), new HashSet<>()) {
            @Override
            public int returnBook(LocalDate localDate) throws BookUnavailableException {
                throw new BookUnavailableException("Weird error");
            }
        };

        model.addBook(badBook);
        ReturnCommand cmd = new ReturnCommand(new BookName("Bad Book"), LocalDate.of(2025, 2, 20));

        CommandException thrown = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Failed to return Bad Book because Weird error", thrown.getMessage());
    }

}
