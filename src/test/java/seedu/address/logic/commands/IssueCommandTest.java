package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PersonBuilder.DEFAULT_EMAIL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class IssueCommandTest {

    private Model model;
    private Book borrowedBook;
    private Book availableBook;
    private Person borrower;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        borrowedBook = new Book(new BookName("Borrowed Book"), new HashSet<>());
        availableBook = new Book(new BookName("Available Book"), new HashSet<>());
        borrower = new PersonBuilder().withEmail(DEFAULT_EMAIL).build();
        borrowedBook.getStatus().issueBook(LocalDate.of(2025, 2, 1), borrower);

        model.addBook(borrowedBook);
        model.addBook(availableBook);
        model.addPerson(borrower);
    }

    @Test
    public void execute_issueAvailableBook_success() throws CommandException {
        IssueCommand cmd = new IssueCommand(availableBook.getName(), borrower.getEmail());
        CommandResult result = cmd.execute(model);
        assertEquals(String.format(IssueCommand.MESSAGE_SUCCESS,
                "Available Book", DEFAULT_EMAIL), result.getFeedbackToUser());
    }

    @Test
    public void execute_issueNonExistentBook_failure() {
        IssueCommand cmd = new IssueCommand(new BookName("Non Existent Book"), borrower.getEmail());
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_issueNonExistentPerson_failure() {
        IssueCommand cmd = new IssueCommand(availableBook.getName(), new Email("nonexistentemail@test.com"));
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_issueBorrowedBook_failure() {
        IssueCommand cmd = new IssueCommand(borrowedBook.getName(), borrower.getEmail());
        CommandException thrown = Assertions.assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(thrown.getMessage(),
                "Failed to issue Borrowed Book because Currently borrowed by Amy Bee from 2025-02-01 till 2025-02-15");
    }

    @Test
    public void execute_returnThrowsUnexpectedBookUnavailableException_fallbackMessage() {
        Book badBook = new Book(new BookName("Bad Book"), new HashSet<>()) {
            @Override
            public void issueBook(LocalDate localDate, Person borrower) throws BookUnavailableException {
                throw new BookUnavailableException("Weird error");
            }
        };

        model.addBook(badBook);
        IssueCommand cmd = new IssueCommand(new BookName("Bad Book"), borrower.getEmail());

        CommandException thrown = Assertions.assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals("Failed to issue Bad Book because Weird error", thrown.getMessage());
    }

    @Test
    public void equals() {
        // Sample data
        BookName bookName1 = new BookName("Book A");
        BookName bookName2 = new BookName("Book B");
        Email email1 = new Email("alice@example.com");
        Email email2 = new Email("bob@example.com");

        // Same values
        IssueCommand command1 = new IssueCommand(bookName1, email1);
        IssueCommand command1Copy = new IssueCommand(bookName1, email1);

        // Different values
        IssueCommand command2 = new IssueCommand(bookName2, email1);
        IssueCommand command3 = new IssueCommand(bookName1, email2);
        IssueCommand command4 = new IssueCommand(bookName1, email1);

        // 1. Same object -> returns true
        assertTrue(command1.equals(command1));

        // 2. Same values -> returns true
        assertTrue(command1.equals(command1Copy));
        assertTrue(command1Copy.equals(command1));

        // 3. Null -> returns false
        assertFalse(command1.equals(null));

        // 4. Different types -> returns false
        assertFalse(command1.equals("some string"));

        // 5. Different bookName -> returns false
        assertFalse(command1.equals(command2));

        // 6. Different email -> returns false
        assertFalse(command1.equals(command3));
    }

    @Test
    public void toString_containsExpectedFields() {
        BookName bookName = new BookName("Some Book");
        Email email = new Email("user@example.com");
        LocalDate localDate = LocalDate.now();

        IssueCommand command = new IssueCommand(bookName, email);
        String output = command.toString();

        // Check that the output string includes the field names and values.
        assertTrue(output.contains("book"));
        assertTrue(output.contains("Some Book"));
        assertTrue(output.contains("person"));
        assertTrue(output.contains("user@example.com"));
        assertTrue(output.contains("localDate"));
        assertTrue(output.contains(localDate.toString()));
    }
}






