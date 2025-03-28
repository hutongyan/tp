package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddBookCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddBookCommand;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.tag.Tag;

public class AddBookCommandParserTest {
    private AddBookCommandParser parser = new AddBookCommandParser();

    @Test
    public void parse_validArgs_success() throws Exception {
        // Example: " b/Some Book t/adventure t/action"
        // Adjust the prefix usage to match your project’s style (e.g., "b/" vs " t/").
        String userInput = " " + PREFIX_BOOK + "Some Book"
                + " " + PREFIX_TAG + "adventure"
                + " " + PREFIX_TAG + "action";

        AddBookCommand command = parser.parse(userInput);

        // Construct the expected Book
        Book expectedBook = new Book(
                new BookName("Some Book"),
                new HashSet<>(Set.of(new Tag("adventure"), new Tag("action")))
        );
        AddBookCommand expectedCommand = new AddBookCommand(expectedBook);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingBookPrefix_failure() {
        // userInput is missing the required PREFIX_BOOK
        String userInput = "Some Book " + PREFIX_TAG + "adventure";
        // The expected error message might be something like:
        // "Invalid command format! \nAddBookCommand usage..."
        // Typically, you pass in the same message your parser uses for invalid input:
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_extraneousPreamble_failure() {
        // There's random text before the prefix
        String userInput = "randomPreamble " + PREFIX_BOOK + "Some Book";
        // Because argMultimap.getPreamble() is not empty, the parser should fail
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateBookPrefix_failure() {
        // If your parser disallows multiple b/ prefixes,
        // having two will trigger a parse failure.
        String userInput = " " + PREFIX_BOOK + "Some Book " + PREFIX_BOOK + "Another Book";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFormat_failure() {
        // No valid prefixes at all
        String userInput = "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
