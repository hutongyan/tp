package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.book.BookName.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteBookCommand;
import seedu.address.model.book.BookName;

/**
 * Contains unit tests for {@code DeleteBookCommandParser}.
 */
public class DeleteBookCommandParserTest {
    private final DeleteBookCommandParser parser = new DeleteBookCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteBookCommand() {
        String userInput = " " + PREFIX_BOOK + "Harry Potter";
        DeleteBookCommand expectedCommand = new DeleteBookCommand(new BookName("Harry Potter"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingBookPrefix_throwsParseException() {
        String userInput = "Harry Potter";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteBookCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyBookName_throwsParseException() {
        String userInput = " " + PREFIX_BOOK;
        assertParseFailure(parser, userInput, MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateBookPrefix_throwsParseException() {
        String userInput = " " + PREFIX_BOOK + "Book1 " + PREFIX_BOOK + "Book2";
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BOOK));
    }
}
