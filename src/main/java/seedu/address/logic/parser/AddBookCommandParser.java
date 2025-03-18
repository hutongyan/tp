package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddBookCommand object.
 */
public class AddBookCommandParser implements Parser<AddBookCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBookCommand
     * and returns an AddBookCommand object for execution.
     *
     * @param args the input arguments
     * @return an AddBookCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BOOK, PREFIX_TAG);

        if (!argMultimap.getValue(PREFIX_BOOK).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_BOOK);
        BookName name = ParserUtil.parseBookName(argMultimap.getValue(PREFIX_BOOK).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Book book = new Book(name, tagList);

        return new AddBookCommand(book);
    }
}
