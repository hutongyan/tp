package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.IssueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookName;
import seedu.address.model.person.Email;

public class IssueCommandParserTest {

    private IssueCommandParser parser = new IssueCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_BOOK + "Some Book " + PREFIX_EMAIL + "example@example.com";
        IssueCommand expectedCommand = new IssueCommand(new BookName("Some Book"), new Email("example@example.com"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingBookPrefix_failure() {
        String userInput = " " + PREFIX_EMAIL + "example@example.com";
        assertParseFailure(parser,
                userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, IssueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingEmailPrefix_failure() {
        String userInput = " " + PREFIX_BOOK + "Some Book";
        assertParseFailure(parser,
                userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, IssueCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateBookPrefix_failure() {
        String userInput = " " + PREFIX_BOOK + "Some Book "
                + PREFIX_BOOK + "Another Book "
                + PREFIX_EMAIL + "example@example.com";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateEmailPrefix_failure() {
        String userInput = " " + PREFIX_BOOK + "Some Book "
                + PREFIX_EMAIL + "example@example.com "
                + PREFIX_EMAIL + "another@example.com";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidFormatWithPreamble_failure() {
        String userInput = " preamble " + PREFIX_BOOK + "Some Book " + PREFIX_EMAIL + "example@example.com";
        assertParseFailure(parser,
                userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, IssueCommand.MESSAGE_USAGE));
    }
}
