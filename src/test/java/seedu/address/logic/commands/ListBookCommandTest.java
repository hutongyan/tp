package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalBooks.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListBookCommand.
 */
public class ListBookCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        ListBookCommand command = new ListBookCommand();
        CommandResult result = command.execute(model);

        StringBuilder expectedMessage = new StringBuilder(ListBookCommand.MESSAGE_SUCCESS);
        expectedModel.getFilteredBookList().forEach(book -> expectedMessage.append("\n").append(book.getName()));

        assert result.getFeedbackToUser().equals(expectedMessage.toString())
                : "Expected feedback does not match actual.";

        assert model.equals(expectedModel)
                : "Model state changed unexpectedly after executing ListBookCommand.";
    }

}
