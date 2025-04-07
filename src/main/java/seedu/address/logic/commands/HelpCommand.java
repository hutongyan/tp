package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Lists all available commands and their usage formats.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows list of all commands and usage.\n"
                                               + "Example: " + COMMAND_WORD;

    public static final String HELP_MESSAGE = "List of available commands:\n"
                                              + "1. add: Adds a user.\n"
                                              + "   Format: add n/NAME p/PHONE e/EMAIL "
                                              + "a/ADDRESS m/MEMBERSHIP [t/TAG]...\n"
                                              + "2. edit: Edits an existing user.\n"
                                              + "   Format: edit INDEX [n/NAME] [p/PHONE] "
                                              + "[e/EMAIL] [a/ADDRESS] [m/MEMBERSHIP] [t/TAG]...\n"
                                              + "3. delete: Deletes a user.\n"
                                              + "   Format: delete INDEX\n"
                                              + "4. list_users: Lists all users with optional filters.\n"
                                              + "   Format: list_users [e/EMAIL] [n/NAME] "
                                              + "[m/MEMBERSHIP] [b/BOOK] [t/TAG]\n"
                                              + "5. find: Finds users by name.\n"
                                              + "   Format: find KEYWORD [MORE_KEYWORDS]...\n"
                                              + "6. add_book: Adds a book.\n"
                                              + "   Format: add_book b/BOOK_NAME\n"
                                              + "7. list_books: Lists all books.\n"
                                              + "   Format: list_books\n"
                                              + "8. delete_book: Deletes a book.\n"
                                              + "   Format: delete_book INDEX\n"
                                              + "9. issue: Issues a book to a user.\n"
                                              + "   Format: issue b/BOOK_NAME e/EMAIL\n"
                                              + "10. return: Returns a book from a user.\n"
                                              + "    Format: return b/BOOK_NAME on d/RETURN_DATE\n"
                                              + "11. list_borrowed_books: Lists borrowed books by user.\n"
                                              + "    Format: list_borrowed_books e/EMAIL\n"
                                              + "12. display_overdue: Displays users with overdue books.\n"
                                              + "    Format: display_overdue\n"
                                              + "13. extend: Extends the duration for which the user can "
                                              + "borrow a book without paying overdue fees.\n"
                                              + "    Format: extend b/BOOK_NAME e/EMAIL\n"
                                              + "14. clear: Clears all users and books.\n"
                                              + "    Format: clear\n"
                                              + "15. exit: Exits the app.\n"
                                              + "    Format: exit\n"
                                              + "16. help: Shows this help message.\n"
                                              + "    Format: help";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(HELP_MESSAGE);
    }

}
