package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookName;

/**
 * Parses input arguments and creates a new ReturnCommand object
 */
public class ReturnCommandParser implements Parser<ReturnCommand> {

    private static final String PREFIX_BOOK = "b/";
    private static final String PREFIX_DATE = "on d/";

    @Override
    public ReturnCommand parse(String args) throws ParseException {
        try {
            String trimmed = args.trim();
            if (!trimmed.contains(PREFIX_BOOK) || !trimmed.contains(PREFIX_DATE)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, getUsage()));
            }

            String[] parts = trimmed.split(PREFIX_BOOK)[1].split(PREFIX_DATE);
            String bookNameStr = parts[0].trim();
            String dateStr = parts[1].trim();

            BookName bookName = new BookName(bookNameStr);
            LocalDate returnDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            return new ReturnCommand(bookName, returnDate);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, getUsage()));
        }
    }

    private String getUsage() {
        return "return b/<book_name> on d/<return_date>\nExample: return b/Percy Jackson on d/20/02/2025";
    }

}
