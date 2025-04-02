package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListBorrowedBooksCommand;
import seedu.address.model.person.Email;

public class ListBorrowedBooksCommandParserTest {
    private ListBorrowedBooksCommandParser parser = new ListBorrowedBooksCommandParser();

    @Test
    public void parse_validArgs_returnsListBorrowedBooksCommand() throws Exception {
        String userInput = " " + PREFIX_EMAIL + " example@example.com";
        ListBorrowedBooksCommand expectedCommand = new ListBorrowedBooksCommand(new Email("example@example.com"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = " invalid args";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListBorrowedBooksCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingEmail_throwsParseException() {
        String userInput = " ";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListBorrowedBooksCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateEmailPrefix_throwsParseException() {
        String userInput = PREFIX_EMAIL + "example@example.com " + PREFIX_EMAIL + "duplicate@example.com";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListBorrowedBooksCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        String userInput = PREFIX_BOOK + "invalidPrefix@example.com";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListBorrowedBooksCommand.MESSAGE_USAGE));
    }
}
