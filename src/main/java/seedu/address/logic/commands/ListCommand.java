package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.Model;
import seedu.address.model.person.UserMatchesPredicate;

/**
 * Lists all persons in the address book to the user with optional filters.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list_users";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_FAILURE = "No persons found";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists users in the system. "
                                               + "Optional filters: \n"
                                               + "Parameters: [e/EMAIL] [n/NAME] [m/MEMBERSHIP] [b/BOOK] [t/TAG]\n"
                                               + "Example: list_users e/alex@example.com "
                                               + "OR list_users m/ACTIVE OR list_users t/Frequent Visitor";

    private final Optional<String> email;
    private final Optional<String> name;
    private final Optional<String> membership;
    private final Optional<String> borrowedBook;
    private final Optional<String> tag;

    /**
     * Constructs a {@code ListCommand} with the given optional filters.
     *
     * @param email The email filter (if provided).
     * @param name The name filter (if provided).
     * @param membership The membership filter (if provided).
     * @param borrowedBook The borrowed book filter (if provided).
     * @param tag The tag filter (if provided).
     */
    public ListCommand(Optional<String> email, Optional<String> name, Optional<String> membership,
                       Optional<String> borrowedBook, Optional<String> tag) {
        this.email = email;
        this.name = name;
        this.membership = membership;
        this.borrowedBook = borrowedBook;
        this.tag = tag;
    }

    /**
     * Executes the list command, updating the model with the filtered list of users.
     *
     * @param model The model of the application.
     * @return A command result indicating success.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int size;
        if (email.isEmpty()
            && name.isEmpty()
            && membership.isEmpty()
            && borrowedBook.isEmpty()
            && tag.isEmpty()) {
            size = model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        } else {
            size = model.updateFilteredPersonList(new UserMatchesPredicate(email, name, membership, borrowedBook, tag));
        }
        return size == 0 ? new CommandResult(MESSAGE_FAILURE) : new CommandResult(MESSAGE_SUCCESS);
    }
}
