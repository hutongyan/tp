package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.address.logic.commands.IssueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new IssueCommand object.
 */
public class IssueCommandParser implements Parser<IssueCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the IssueCommand
     * and returns an IssueCommand object for execution
     *
     * @param args the input arguments
     * @return an IssueCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public IssueCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BOOK, PREFIX_EMAIL);

        if (!argMultimap.getValue(PREFIX_BOOK).isPresent()
                || !argMultimap.getValue(PREFIX_EMAIL).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IssueCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_BOOK, PREFIX_EMAIL);
        return new IssueCommand(ParserUtil.parseBookName(argMultimap.getValue(PREFIX_BOOK).get()),
                ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
    }
}
