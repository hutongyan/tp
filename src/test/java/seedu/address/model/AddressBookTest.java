package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.exceptions.AddressBookException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {
    private static final LocalDate TEST_DATE = LocalDate.of(2025, 4, 3);
    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() throws CommandException {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson((Person) null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() throws CommandException {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() throws CommandException {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void issueBook_issuesBookToPerson() throws BookUnavailableException, AddressBookException {
        AddressBook addressBook = new AddressBook();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        Person person = new PersonBuilder().withEmail("test@example.com").build();
        addressBook.addBook(book);
        addressBook.addPerson(person);
        addressBook.issueBook(book.getName(), person.getEmail(), LocalDate.now());
        assertTrue(person.hasBorrowed(book));
        assertTrue(book.isIssued());
    }

    @Test
    public void issueBook_bookAlreadyIssued_throwsBookUnavailableException() throws AddressBookException {
        AddressBook addressBook = new AddressBook();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        Person person1 = new PersonBuilder().withEmail("test1@example.com").build();
        Person person2 = new PersonBuilder().withEmail("test2@example.com").build();
        addressBook.addBook(book);
        addressBook.addPerson(person1);
        addressBook.addPerson(person2);
        addressBook.issueBook(book.getName(), person1.getEmail(), LocalDate.now());
        assertThrows(BookUnavailableException.class, () -> addressBook.issueBook(book.getName(),
                person2.getEmail(), LocalDate.now()));
    }

    @Test
    public void returnBook_returnsBookToLibrary() throws BookUnavailableException, AddressBookException {
        AddressBook addressBook = new AddressBook();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        Person person = new PersonBuilder().withEmail("test@example.com").build();
        addressBook.addBook(book);
        addressBook.addPerson(person);
        addressBook.issueBook(book.getName(), person.getEmail(), LocalDate.now());
        int fine = addressBook.returnBook(book.getName(), LocalDate.now().plusDays(1));
        assertFalse(person.hasBorrowed(book));
        assertFalse(book.isIssued());
        assertEquals(0, fine); // Assuming no fine for 1 day late
    }

    @Test
    public void returnBook_bookNotIssued_throwsBookUnavailableException() throws AddressBookException {
        AddressBook addressBook = new AddressBook();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        addressBook.addBook(book);
        assertThrows(BookUnavailableException.class, () -> addressBook.returnBook(book.getName(), LocalDate.now()));
    }

    @Test
    public void listBorrowedBook_noBooksBorrowed_returnsEmptyString() throws AddressBookException {
        Person person = new PersonBuilder().withEmail("test@example.com").build();
        addressBook.addPerson(person);
        assertEquals("", addressBook.listBorrowedBook(person.getEmail()));
    }

    @Test
    public void listBorrowedBook_singleBookBorrowed_returnsBookName() throws AddressBookException {
        Person person = new PersonBuilder().withEmail("test@example.com").build();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        addressBook.addPerson(person);
        addressBook.addBook(book);
        addressBook.issueBook(book.getName(), person.getEmail(), TEST_DATE);
        assertEquals("Test Book (Due: 2025-04-17)",
                addressBook.listBorrowedBook(person.getEmail()));
    }

    @Test
    public void listBorrowedBook_multipleBooksBorrowed_returnsCommaSeparatedBookNames() throws AddressBookException {
        Person person = new PersonBuilder().withEmail("test@example.com").build();
        Book book1 = new Book(new BookName("Test Book 1"), new HashSet<>());
        Book book2 = new Book(new BookName("Test Book 2"), new HashSet<>());
        addressBook.addPerson(person);
        addressBook.addBook(book1);
        addressBook.addBook(book2);
        addressBook.issueBook(book1.getName(), person.getEmail(), TEST_DATE);
        addressBook.issueBook(book2.getName(), person.getEmail(), TEST_DATE);
        assertEquals("Test Book 1 (Due: 2025-04-17), Test Book 2 (Due: 2025-04-17)",
                addressBook.listBorrowedBook(person.getEmail()));
    }

    @Test
    public void listBorrowedBook_personNotInAddressBook_throwsAddressBookException() {
        Email email = new Email("nonexistent@example.com");
        assertThrows(AddressBookException.class, () -> addressBook.listBorrowedBook(email));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Book> books = FXCollections.observableArrayList();
        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }
    }

}
