package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PersonBuilder.DEFAULT_EMAIL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.PersonBuilder;

public class IssueCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IssueCommand(null, null));
    }

    @Test
    public void execute_bookAcceptedByModel_issueSuccessful() throws Exception {
        Book validBook = new BookBuilder().build();
        Email validEmail = new Email(DEFAULT_EMAIL);
        Person validPerson = new PersonBuilder().build();
        model.addBook(validBook);
        model.addPerson(validPerson);
        assertTrue(!validBook.isIssued());
        IssueCommand issueCommand = new IssueCommand(validBook.getName(), validEmail);
        CommandResult commandResult = issueCommand.execute(model);
        assertEquals(commandResult.getFeedbackToUser(),
                String.format(IssueCommand.MESSAGE_SUCCESS,
                        Messages.format(validBook),
                        Messages.format(validPerson)));
    }
}




