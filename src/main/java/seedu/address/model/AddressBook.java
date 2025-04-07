package seedu.address.model;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookNotBorrowedException;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.book.exceptions.DifferentBorrowerException;
import seedu.address.model.exceptions.AddressBookException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.UniqueList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueList<Person> persons;

    private final UniqueList<Book> books;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueList<>();
        books = new UniqueList<>();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) throws AddressBookException {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) throws AddressBookException {
        this.persons.set(persons);
    }

    public void setBooks(List<Book> books) throws AddressBookException {
        this.books.set(books);
    }


    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) throws AddressBookException {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setBooks(newData.getBookList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same email as {@code email} exists in the address book.
     */
    public boolean hasPerson(Email email) {
        requireNonNull(email);
        return persons.containsField(email, Person::getEmail);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) throws AddressBookException {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) throws AddressBookException {
        requireNonNull(editedPerson);
        persons.set(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) throws AddressBookException {
        persons.remove(key);
        UniqueList<Book> booksToRemove = key.getBorrowedBooks();
        for (Book book : booksToRemove) {
            book.returnBook(LocalDate.now());
        }
        key.clearBorrowedBooks();
    }
    /**
     * Returns the person with the same email as {@code email} exists in the address book.
     */
    public Person getPerson(Email email) throws AddressBookException {
        requireNonNull(email);
        Predicate<Person> predicate = p -> p.getEmail().equals(email);
        Person person = persons.get(predicate);
        if (isNull(person)) {
            throw new AddressBookException("Person not found");
        }
        return person;
    }

    /**
     * Returns the name of the person with the specified email.
     *
     * @param email The email of the person whose name is to be retrieved. Must not be null.
     * @return The name of the person with the specified email.
     * @throws AddressBookException If no person with the specified email is found.
     */
    public Name getPersonName(Email email) throws AddressBookException {
        requireNonNull(email);
        Predicate<Person> predicate = p -> p.getEmail().equals(email);
        return persons.get(predicate).getName();
    }

    public void removeBook(Book key) throws AddressBookException {
        books.remove(key);
    }
    /**
     * Returns true if a book with the same name as {@code book} exists in the address book.
     */
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return books.contains(book);
    }

    /**
     * Returns true if a book with the same name as {@code bookName} exists in the address book.
     */
    public boolean hasBook(BookName bookName) {
        requireNonNull(bookName);
        return books.containsField(bookName, Book::getName);
    }

    /**
     * Adds a book to the address book.
     * The book must not already exist in the address book.
     */
    public void addBook(Book book) throws AddressBookException {
        books.add(book);
    }

    /**
     * Returns the book with the same bookname as {@code bookName} exists in the address book.
     */
    public Book getBook(BookName bookName) throws AddressBookException {
        requireNonNull(bookName);
        return getBookList()
                .stream()
                .filter(book -> book.getName().equals(bookName))
                .findFirst()
                .orElseThrow(() -> new AddressBookException("Book not found"));
    }
    /**
     * Issues a book to a person
     *
     * @param bookName
     * @param email
     * @param localDate
     *
     *
     * @throws BookUnavailableException if the book is already issued
     *
     */
    public void issueBook(BookName bookName, Email email, LocalDate localDate) throws BookUnavailableException {
        requireNonNull(bookName);
        requireNonNull(email);
        requireNonNull(localDate);
        Book bookToIssue = getBook(bookName);
        Person personToIssue = getPerson(email);
        bookToIssue.issueBook(localDate, personToIssue);
        personToIssue.borrows(bookToIssue);
    }
    /**
     * Returns a book to the library
     *
     * @param bookName
     * @param returnDate
     * @return fine amount if any
     */
    public int returnBook(BookName bookName, LocalDate returnDate) throws BookUnavailableException {
        requireNonNull(bookName);
        requireNonNull(returnDate);
        Book bookToReturn = getBook(bookName);
        Person borrower = bookToReturn.getBorrower();
        try {
            borrower.returns(bookToReturn);
        } catch (NullPointerException e) {
            throw new BookUnavailableException("Available");
        }
        return bookToReturn.returnBook(returnDate);
    }

    /**
     * Extends the duration for which a book can be borrowed without overdue fees
     *
     * @param bookName
     * @param email
     *
     * @throws BookNotBorrowedException if the book is not already borrowed
     * @throws DifferentBorrowerException if the book is borrowed by a different person
     */
    public void extendBook(BookName bookName, Email email) throws BookNotBorrowedException,
            DifferentBorrowerException {
        requireNonNull(bookName);
        requireNonNull(email);
        Book bookToExtend = getBook(bookName);
        Person personToExtend = getPerson(email);
        bookToExtend.extendBook(personToExtend);
    }

    /**
     * Returns a string representation of the list of books borrowed by the specified user.
     *
     * @param email The email of the user whose borrowed books are to be listed. Must not be null.
     * @return A string representation of the list of books borrowed by the user.
     */
    public String listBorrowedBook(Email email) throws AddressBookException {
        requireNonNull(email);
        Person user = getPerson(email);
        return user.getBorrowedBooks().getField(book -> book.getName()
                + " (Due: "
                + book.getStatus().getReturnDate() + ")");
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Book> getBookList() {
        return books.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons) && books.equals(otherAddressBook.books);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    public Person getBorrower(BookName bookName) {
        return getBook(bookName).getStatus().getBorrower();
    }
}
