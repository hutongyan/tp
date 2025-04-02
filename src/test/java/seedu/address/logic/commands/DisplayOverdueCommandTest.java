package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code DisplayOverdueCommand}.
 */
public class DisplayOverdueCommandTest {

    @Test
    public void execute_noOverdueBooks_returnsNoOverdueMessage() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Book book = new Book(new BookName("Clean Code"), new HashSet<>());
        model.addBook(book);

        DisplayOverdueCommand command = new DisplayOverdueCommand();
        CommandResult result = command.execute(model);

        assertEquals("There are no overdue books.", result.getFeedbackToUser());
    }

    @Test
    public void execute_withOverdueBook_displaysOverdueBook() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Person borrower = new PersonBuilder().withName("Alice").build();
        model.addPerson(borrower);

        Book book = new Book(new BookName("Effective Java"), new HashSet<>());
        book.getStatus().issueBook(LocalDate.now().minusDays(20), borrower);

        model.addBook(book);

        DisplayOverdueCommand command = new DisplayOverdueCommand();
        CommandResult result = command.execute(model);

        String expected = "Overdue books listed below:\n\n"
                + book.getName() + "; Status: " + book.getStatus().checkStatus() + "; Tags: ";

        assertEquals(expected, result.getFeedbackToUser());
    }

    @Test
    public void execute_withNonOverdueBorrowedBook_doesNotListIt() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person borrower = new PersonBuilder().withName("Bob").build();
        model.addPerson(borrower);

        Book book = new Book(new BookName("Java Concurrency"), new HashSet<>());
        book.getStatus().issueBook(LocalDate.now(), borrower);
        model.addBook(book);

        CommandResult result = new DisplayOverdueCommand().execute(model);

        assertEquals("There are no overdue books.", result.getFeedbackToUser());
    }

}
