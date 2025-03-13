package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;


/**
 * Parses input arguments and creates a new {@code ListCommand} object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code ListCommand}.
     *
     * @param args The command arguments as a string.
     * @return The parsed {@code ListCommand}.
     * @throws ParseException If the user input does not conform to expected format.
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_EMAIL, PREFIX_NAME, PREFIX_MEMBER, PREFIX_BOOK, PREFIX_TAG);

        Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);
        Optional<String> name = argMultimap.getValue(PREFIX_NAME);
        Optional<String> membership = argMultimap.getValue(PREFIX_MEMBER);
        Optional<String> borrowedBook = argMultimap.getValue(PREFIX_BOOK);
        Optional<String> tag = argMultimap.getValue(PREFIX_TAG);

        return new ListCommand(email, name, membership, borrowedBook, tag);
    }
}
