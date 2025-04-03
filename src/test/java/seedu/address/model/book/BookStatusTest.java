package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.book.BookStatus.Status.AVAILABLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class BookStatusTest {
    public static final LocalDate DEFAULT_ISSUEDATE = LocalDate.of(2025, 10, 10);
    public static final LocalDate DEFAULT_RETURNDATE = LocalDate.of(2025, 10, 24);
    public static final Person DEFAULT_MEMBER = new PersonBuilder().build();
    @Test
    public void testIssueBook_whenAvailable_setStatusAndDates() {
        BookStatus bookStatus = new BookStatus();
        bookStatus.issueBook(DEFAULT_ISSUEDATE, DEFAULT_MEMBER);
        assertEquals(BookStatus.Status.BORROWED, bookStatus.getStatus());
        assertEquals(DEFAULT_ISSUEDATE, bookStatus.getIssueDate());
        assertEquals(DEFAULT_RETURNDATE, bookStatus.getReturnDate());
        assertEquals(DEFAULT_MEMBER, bookStatus.getBorrower());
    }
    @Test
    public void testIssueBook_whenBorrowed_throwException() {
        BookStatus bookStatus = new BookStatus();
        bookStatus.issueBook(DEFAULT_ISSUEDATE, DEFAULT_MEMBER);
        assertThrows(BookUnavailableException.class, () -> bookStatus.issueBook(DEFAULT_ISSUEDATE, DEFAULT_MEMBER));
    }
    @Test
    public void testReturnBook_whenBorrowed_setStatusToAvailable() {
        BookStatus bookStatus = new BookStatus();
        bookStatus.issueBook(DEFAULT_ISSUEDATE, DEFAULT_MEMBER);
        bookStatus.returnBook();
        assertEquals(AVAILABLE, bookStatus.getStatus());
    }
    @Test
    public void testReturnBook_whenAvailable_throwException() {
        BookStatus bookStatus = new BookStatus();
        assertThrows(BookUnavailableException.class, bookStatus::returnBook);
    }
    @Test
    public void testCheckStatus() {
        BookStatus bookStatus = new BookStatus();
        assertEquals("Available", bookStatus.checkStatus());
    }
    @Test
    public void testCheckStatus_whenBorrowed_returnsCorrectString() {
        BookStatus bookStatus = new BookStatus();
        bookStatus.issueBook(DEFAULT_ISSUEDATE, DEFAULT_MEMBER);
        assertEquals("Currently borrowed by " + DEFAULT_MEMBER.getName()
                        + " from " + DEFAULT_ISSUEDATE
                        + " till " + DEFAULT_RETURNDATE,
                bookStatus.checkStatus());
    }
    @Test
    public void testCalculateFines_whenBorrowedAndNotOverdue_returnsZero() {
        BookStatus bookStatus = new BookStatus();
        bookStatus.issueBook(DEFAULT_ISSUEDATE, DEFAULT_MEMBER);
        assertEquals(0, bookStatus.calculateFines(LocalDate.of(2025, 10, 24)));
    }
    @Test
    public void testCalculateFines_whenBorrowedAndOverdue_returnsCorrectAmount() {
        BookStatus bookStatus = new BookStatus();
        bookStatus.issueBook(DEFAULT_ISSUEDATE, DEFAULT_MEMBER);
        assertEquals(1, bookStatus.calculateFines(LocalDate.of(2025, 10, 25)));
    }
    @Test
    public void testCalculateFines_whenAvailable_returnsZero() {
        BookStatus bookStatus = new BookStatus();
        assertEquals(0, bookStatus.calculateFines(LocalDate.of(2025, 10, 24)));
    }
}