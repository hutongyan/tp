package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DELETE_BOOK_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;


/**
 * Contains integration tests (interaction with the Model) for {@code DeleteBookCommand}.
 */
public class DeleteBookCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validBookName_success() throws Exception {
        Book bookToDelete = model.getFilteredBookList().get(0);
        DeleteBookCommand deleteBookCommand = new DeleteBookCommand(bookToDelete.getName());
        String expectedMessage = String.format(MESSAGE_DELETE_BOOK_SUCCESS, Messages.format(bookToDelete));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        CommandResult result = deleteBookCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidBookName_throwsCommandException() {
        BookName invalidBookName = new BookName("Nonexistent Book");
        DeleteBookCommand deleteBookCommand = new DeleteBookCommand(invalidBookName);
        assertThrows(CommandException.class, () -> deleteBookCommand.execute(model));
    }

    @Test
    public void equals() {
        BookName firstBookName = new BookName("First Book");
        BookName secondBookName = new BookName("Second Book");

        DeleteBookCommand deleteFirstCommand = new DeleteBookCommand(firstBookName);
        DeleteBookCommand deleteSecondCommand = new DeleteBookCommand(secondBookName);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBookCommand deleteFirstCommandCopy = new DeleteBookCommand(firstBookName);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        BookName bookName = new BookName("Sample Book");
        DeleteBookCommand deleteBookCommand = new DeleteBookCommand(bookName);
        String expected = DeleteBookCommand.class.getCanonicalName() + "{bookName=" + bookName + "}";
        assertEquals(expected, deleteBookCommand.toString());
    }
}
