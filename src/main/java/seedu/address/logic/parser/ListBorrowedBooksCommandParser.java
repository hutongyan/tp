package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import seedu.address.logic.commands.ListBorrowedBooksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;

/**
 * Parses input arguments and creates a new ListBorrwedBooksCommand object.
 */
public class ListBorrowedBooksCommandParser implements Parser<ListBorrowedBooksCommand> {
    /**
     * Parses input arguments and creates a new ListBorrowedBooksCommand object.
     *
     * @param args The input arguments to parse. Must not be null.
     * @return A new ListBorrowedBooksCommand object.
     * @throws ParseException If the input arguments are invalid or the email is not present.
     */
    public ListBorrowedBooksCommand parse(String args) throws ParseException {
        System.out.println("Parsing input: " + args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMAIL);

        if (!argMultimap.getValue(PREFIX_EMAIL).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListBorrowedBooksCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EMAIL);
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        return new ListBorrowedBooksCommand(email);
    }
}
