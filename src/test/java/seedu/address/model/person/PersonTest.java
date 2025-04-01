package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.util.UniqueList;
import seedu.address.testutil.PersonBuilder;


public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSame(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSame(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSame(editedAlice));

        // same email, all other attributes different -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSame(editedAlice));

        // different email, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSame(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void getBorrowedBooks_returnsCorrectList() {
        Person person = new PersonBuilder().build();
        Book book1 = new Book(new BookName("Test Book 1"), new HashSet<>());
        Book book2 = new Book(new BookName("Test Book 2"), new HashSet<>());
        person.borrows(book1);
        person.borrows(book2);
        UniqueList<Book> borrowedBooks = person.getBorrowedBooks();
        assertTrue(borrowedBooks.contains(book1));
        assertTrue(borrowedBooks.contains(book2));
    }

    @Test
    public void getBorrowedBooks_returnsEmptyListWhenNoBooksBorrowed() {
        Person person = new PersonBuilder().build();
        UniqueList<Book> borrowedBooks = person.getBorrowedBooks();
        assertTrue(borrowedBooks.asUnmodifiableObservableList().isEmpty());
    }

    @Test
    public void borrows_addsBookToBorrowedList() {
        Person person = new PersonBuilder().build();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        person.borrows(book);
        assertTrue(person.hasBorrowed(book));
    }

    @Test
    public void returns_removesBookFromBorrowedList() throws BookUnavailableException {
        Person person = new PersonBuilder().build();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        person.borrows(book);
        person.returns(book);
        assertFalse(person.hasBorrowed(book));
    }

    @Test
    public void returns_throwsBookUnavailableException_whenBookNotBorrowed() {
        Person person = new PersonBuilder().build();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        assertThrows(BookUnavailableException.class, () -> person.returns(book));
    }

    @Test
    public void borrows_throwsDuplicateBookException_whenAddingDuplicateBook() {
        Person person = new PersonBuilder().build();
        Book book = new Book(new BookName("Test Book"), new HashSet<>());
        person.borrows(book);
        assertThrows(DuplicateBookException.class, () -> person.borrows(book));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", membership=" + ALICE.getMembership() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
