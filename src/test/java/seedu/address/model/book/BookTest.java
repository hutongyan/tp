package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_A;
import static seedu.address.testutil.TypicalBooks.BOOK_B;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.person.Person;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.PersonBuilder;

public class BookTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Book book = new BookBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> book.getTags().remove(0));
    }

    @Test
    public void isSameBook() {
        // same object -> returns true
        assertTrue(BOOK_A.isSame(BOOK_A));

        // null -> returns false
        assertFalse(BOOK_A.isSame(null));

        //same bookname, all other attributes different -> returns true
        Book editedBookA = new BookBuilder(BOOK_A).withTags("newTag").build();
        assertTrue(BOOK_A.isSame(editedBookA));

        //same tag, all other attributes different -> returns false
        editedBookA = new BookBuilder(BOOK_A).withName("newName").build();
        assertFalse(BOOK_A.isSame(editedBookA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Book bookACopy = new BookBuilder(BOOK_A).build();
        assertTrue(BOOK_A.equals(bookACopy));

        // same object -> returns true
        assertTrue(BOOK_A.equals(BOOK_A));

        // null -> returns false
        assertFalse(BOOK_A.equals(null));

        // different type -> returns false
        assertFalse(BOOK_A.equals(5));

        // different book -> returns false
        assertFalse(BOOK_A.equals(BOOK_B));

        // different name -> returns false
        Book editedBookA = new BookBuilder(BOOK_A).withName("newName").build();
        assertFalse(BOOK_A.equals(editedBookA));

        // different tags -> returns false
        editedBookA = new BookBuilder(BOOK_A).withTags("newTag").build();
        assertFalse(BOOK_A.equals(editedBookA));
    }

    @Test
    public void checkStatus_returnsAvailableWhenNotIssued() {
        Book book = new BookBuilder().build();
        assertEquals("Available", book.checkStatus());
    }

    @Test
    public void checkStatus_returnsBorrowedWhenIssued() throws BookUnavailableException {
        Book book = new BookBuilder().build();
        Person person = new PersonBuilder().build();
        LocalDate issueDate = LocalDate.now();
        LocalDate returnDate = issueDate.plusDays(14);
        book.issueBook(issueDate, person);
        assertEquals("Currently borrowed by " + person.getName()
                + " from " + issueDate + " till " + returnDate, book.checkStatus());
    }

    @Test
    public void checkStatus_returnsAvailableAfterReturn() throws BookUnavailableException {
        Book book = new BookBuilder().build();
        Person person = new PersonBuilder().build();
        book.issueBook(LocalDate.now(), person);
        book.returnBook(LocalDate.now().plusDays(1));
        assertEquals("Available", book.checkStatus());
    }

    @Test
    public void returnBook_dateBeforeIssueDate_throwsBookUnavailableException() {
        Book book = new BookBuilder().build();
        Person person = new PersonBuilder().build();
        LocalDate issueDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2022, 12, 31); // Date before the issue date
        // Issue the book first
        book.issueBook(issueDate, person);
        // Attempt to return the book with a date before the issue date
        assertThrows(BookUnavailableException.class, () -> book.returnBook(returnDate));
    }
}
