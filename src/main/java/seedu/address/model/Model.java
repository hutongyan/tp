package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same identity as {@code email} exists in the address book.
     */
    boolean hasPerson(Email email);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the name of the person with the specified email.
     *
     * @param email The email of the person whose name is to be retrieved. Must not be null.
     * @return The name of the person with the specified email.
     */
    Name getPersonName(Email email);

    /**
     * Returns a string representation of the list of books borrowed by the specified user.
     *
     * {@code email} The email of the user whose borrowed books are to be listed. Must not be null.
     * @return A string representation of the list of books borrowed by the user.
     */
    String listBorrowedBooks(Email email);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds the given book.
     * {@code book} must not already exist in the address book.
     */
    void addBook(Book book);

    /**
     * Returns true if a book with the same identity as {@code book} exists in the address book.
     */
    boolean hasBook(Book book);

    /**
     * Returns true if a book with the same identity as {@code bookName} exists in the address book.
     */
    boolean hasBook(BookName bookName);

    /**
     * Deletes the given book.
     * The book must exist in the address book.
     */
    void deleteBook(Book target);

    /**
     * Issues a book to a target user
     */
    void issueBook(BookName bookName, Email email, LocalDate localDate);

    /**
     * Returns the book with the same identity as {@code book} exists in the address book.
     */
    int returnBook(BookName bookName, LocalDate localDate);

    /**
     * Extends the duration for which a book can be borrowed without overdue fees
     */
    void extendBook(BookName bookName, Email email, LocalDate localDate);

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    void updateFilteredBookList(Predicate<Book> predicate);
}
