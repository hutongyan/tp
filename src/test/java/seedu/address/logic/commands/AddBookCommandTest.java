package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_ADD_BOOK_FAIL;
import static seedu.address.logic.Messages.MESSAGE_ADD_BOOK_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.BookBuilder;

public class AddBookCommandTest {
    @Test
    public void constructor_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBookCommand(null));
    }
    @Test
    public void execute_bookAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();
        CommandResult commandResult = new AddBookCommand(validBook).execute(modelStub);
        assertEquals(String.format(MESSAGE_ADD_BOOK_SUCCESS, Messages.format(validBook)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBook), modelStub.booksAdded);
    }
    @Test
    public void execute_duplicateBook_throwsCommandException() {
        Book validBook = new BookBuilder().build();
        AddBookCommand addBookCommand = new AddBookCommand(validBook);
        ModelStub modelStub = new ModelStubWithBook(validBook);
        assertThrows(CommandException.class,
                MESSAGE_ADD_BOOK_FAIL, () -> addBookCommand.execute(modelStub));
    }
    @Test
    public void equals() {
        Book hp = new BookBuilder().withName("Harry Potter").build();
        Book pj = new BookBuilder().withName("Percy Jackson").build();
        AddBookCommand addHpCommand = new AddBookCommand(hp);
        AddBookCommand addPjCommand = new AddBookCommand(pj);
        //same object -> returns true
        assertTrue(addHpCommand.equals(addHpCommand));
        //same values -> returns true
        AddBookCommand addHpCommandCopy = new AddBookCommand(hp);
        assertTrue(addHpCommand.equals(addHpCommandCopy));
        //different types -> returns false
        assertFalse(addHpCommand.equals(1));
        //null -> returns false
        assertFalse(addHpCommand.equals(null));
        //different book -> returns false
        assertFalse(addHpCommand.equals(addPjCommand));
    }
    @Test
    public void toStringMethod() {
        Book hp = new BookBuilder().withName("Harry Potter").build();
        AddBookCommand addHpCommand = new AddBookCommand(hp);
        String expected = AddBookCommand.class.getCanonicalName() + "{book=" + hp + "}";
        assertEquals(expected, addHpCommand.toString());
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Email email) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String listBorrowedBooks(Email email) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Name getPersonName(Email email) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBook(BookName bookName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBook(Book target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void issueBook(BookName bookName, Email email, LocalDate localDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int returnBook(BookName bookName, LocalDate localDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void extendBook(BookName bookName, Email email, LocalDate localDate) {
            throw new AssertionError("This method should not be called.");
        }
    }
    private class ModelStubWithBook extends ModelStub {
        private final Book book;

        ModelStubWithBook(Book book) {
            requireNonNull(book);
            this.book = book;
        }

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return this.book.isSame(book);
        }
    }
    /**
     * A Model stub that always accept the book being added.
     */
    private class ModelStubAcceptingBookAdded extends ModelStub {
        final ArrayList<Book> booksAdded = new ArrayList<>();

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return booksAdded.stream().anyMatch(book::isSame);
        }

        @Override
        public void addBook(Book book) {
            requireNonNull(book);
            booksAdded.add(book);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
