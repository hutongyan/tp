package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookName;

/**
 * Contains unit tests for ReturnCommandParser.
 */
public class ReturnCommandParserTest {

    private final ReturnCommandParser parser = new ReturnCommandParser();

    @Test
    public void parse_validInput_success() throws Exception {
        String input = "b/Percy Jackson on d/20/02/2025";
        ReturnCommand command = parser.parse(input);

        assertEquals(new BookName("Percy Jackson"), getField(command, "bookName", BookName.class));
        assertEquals(LocalDate.of(2025, 2, 20), getField(command, "returnDate", LocalDate.class));
    }

    @Test
    public void parse_missingBookPrefix_throwsParseException() {
        String input = "Percy Jackson on d/20/02/2025";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_missingDatePrefix_throwsParseException() {
        String input = "b/Percy Jackson 20/02/2025";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String input = "b/Percy Jackson on d/2025-02-20";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    private <T> T getField(Object obj, String fieldName, Class<T> clazz) throws Exception {
        var field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return clazz.cast(field.get(obj));
    }

}
