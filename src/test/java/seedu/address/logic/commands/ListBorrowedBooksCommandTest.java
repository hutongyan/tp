package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Membership;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;


public class ListBorrowedBooksCommandTest {
    private Model model;
    private Person validUser;
    private Person invalidUser;
    private Email validEmail;
    private Email invalidEmail;
    private BookName bookNameOne;
    private BookName bookNameTwo;
    private Book bookOne;
    private Book bookTwo;
    private LocalDate localDate = LocalDate.now();

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        validUser = new Person(
                new Name("Valid User"),
                new Phone("99999999"),
                new Email("valid@example.com"),
                new Address("Somewhere"),
                Membership.ACTIVE,
                new HashSet<>()
        );

        invalidUser = new Person(
                new Name("invalid User"),
                new Phone("99999999"),
                new Email("invalid@example.com"),
                new Address("Somewhere"),
                Membership.ACTIVE,
                new HashSet<>()
        );

        validEmail = new Email("valid@example.com");
        invalidEmail = new Email("invalid@example.com");

        bookNameOne = new BookName("Book1");
        bookNameTwo = new BookName("Book2");

        bookOne = new Book(bookNameOne, new HashSet<>());
        bookTwo = new Book(bookNameTwo, new HashSet<>());

        model.addPerson(validUser);
        model.addBook(bookOne);
        model.addBook(bookTwo);
    }

    @Test
    void execute_withValidEmail_listsBorrowedBooks() throws CommandException {
        model.issueBook(bookNameOne, validEmail, localDate);
        model.issueBook(bookNameTwo, validEmail, localDate);
        ListBorrowedBooksCommand cmd = new ListBorrowedBooksCommand(validEmail);
        CommandResult result = cmd.execute(model);
        assertEquals("Book1, Book2"
                + "\n"
                + "Listed all borrowed books by: Valid User",
                result.getFeedbackToUser());
    }

    @Test
    void execute_withInvalidEmail_throwsCommandException() {
        ListBorrowedBooksCommand invalidCommand = new ListBorrowedBooksCommand(invalidEmail);
        assertThrows(CommandException.class, () -> invalidCommand.execute(model));
    }

    @Test
    void execute_withNoBorrowedBooks_returnsNoBorrowedBooksMessage() throws CommandException {
        model.addPerson(invalidUser);
        ListBorrowedBooksCommand noBooksCommand = new ListBorrowedBooksCommand(invalidEmail);
        CommandResult result = noBooksCommand.execute(model);

        assertEquals("User is currently not borrowing any books.", result.getFeedbackToUser());
    }

    @Test
    void equals_sameObject_returnsTrue() {
        ListBorrowedBooksCommand command = new ListBorrowedBooksCommand(new Email("example@example.com"));
        assertEquals(command, command);
    }

    @Test
    void equals_differentType_returnsFalse() {
        ListBorrowedBooksCommand command = new ListBorrowedBooksCommand(new Email("example@example.com"));
        assertNotEquals(command, new Object());
    }

    @Test
    void equals_null_returnsFalse() {
        ListBorrowedBooksCommand command = new ListBorrowedBooksCommand(new Email("example@example.com"));
        assertNotEquals(command, null);
    }

    @Test
    void equals_sameEmail_returnsTrue() {
        ListBorrowedBooksCommand command1 = new ListBorrowedBooksCommand(new Email("example@example.com"));
        ListBorrowedBooksCommand command2 = new ListBorrowedBooksCommand(new Email("example@example.com"));
        assertEquals(command1, command2);
    }

    @Test
    void equals_differentEmail_returnsFalse() {
        ListBorrowedBooksCommand command1 = new ListBorrowedBooksCommand(new Email("example@example.com"));
        ListBorrowedBooksCommand command2 = new ListBorrowedBooksCommand(new Email("different@example.com"));
        assertNotEquals(command1, command2);
    }

    @Test
    void hashCode_sameEmail_sameHashCode() {
        ListBorrowedBooksCommand command1 = new ListBorrowedBooksCommand(new Email("example@example.com"));
        ListBorrowedBooksCommand command2 = new ListBorrowedBooksCommand(new Email("example@example.com"));
        assertEquals(command1.hashCode(), command2.hashCode());
    }

    @Test
    void hashCode_differentEmail_differentHashCode() {
        ListBorrowedBooksCommand command1 = new ListBorrowedBooksCommand(new Email("example@example.com"));
        ListBorrowedBooksCommand command2 = new ListBorrowedBooksCommand(new Email("different@example.com"));
        assertNotEquals(command1.hashCode(), command2.hashCode());
    }
}
