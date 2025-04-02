package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookStatus;

/**
 * Displays a list of overdue books and the users who have borrowed them.
 */
public class DisplayOverdueCommand extends Command {

    public static final String COMMAND_WORD = "display_overdue";
    public static final String MESSAGE_SUCCESS = "Overdue books listed below:";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        LocalDate today = LocalDate.now();
        List<Book> overdueBooks = model.getAddressBook().getBookList().stream()
                .filter(book -> {
                    BookStatus status = book.getStatus();
                    return status != null && status.checkStatus().startsWith("Currently borrowed")
                            && status.calculateFines(today) > 0;
                })
                .collect(Collectors.toList());

        if (overdueBooks.isEmpty()) {
            return new CommandResult("There are no overdue books.");
        }

        String result = MESSAGE_SUCCESS + "\n\n" + overdueBooks.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        return new CommandResult(result);
    }
}

