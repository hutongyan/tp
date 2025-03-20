package seedu.address.model.book;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.person.Person;

/**
 * Represents the status of a book in the library.
 */
public class BookStatus {

    /**
     * Enum representing the status of a book.
     */
    public enum Status {
        AVAILABLE, BORROWED
    }

    private Status status;
    private Person borrowedMember;
    private LocalDate issueDate;
    private LocalDate returnDate;

    /**
     * Constructs a {@code BookStatus} with default values.
     */
    public BookStatus() {
        this.status = Status.AVAILABLE;
        this.borrowedMember = null;
        this.issueDate = null;
        this.returnDate = null;
    }

    /**
     * Issues the book to a member.
     *
     * @param issueDate the date the book is issued
     * @param member the member to whom the book is issued
     * @throws BookUnavailableException if the book is already borrowed
     */
    public void issueBook(LocalDate issueDate, Person member) throws BookUnavailableException {
        if (status == Status.BORROWED) {
            throw new BookUnavailableException(checkStatus());
        }
        this.status = Status.BORROWED;
        this.issueDate = issueDate;
        this.returnDate = issueDate.plusDays(14);
        this.borrowedMember = member;
    }

    /**
     * Returns the book.
     *
     * @throws BookUnavailableException if the book is already available
     */
    public void returnBook() throws BookUnavailableException {
        if (status == Status.AVAILABLE) {
            throw new BookUnavailableException(checkStatus());
        }
        this.status = Status.AVAILABLE;
        this.issueDate = null;
        this.returnDate = null;
        this.borrowedMember = null;
    }

    /**
     * Checks the status of the book.
     *
     * @return the status of the book
     */
    public String checkStatus() {
        if (status == Status.AVAILABLE) {
            return "Available";
        } else {
            return "Currently borrowed by " + borrowedMember.getName()
                + " from " + issueDate + " till " + returnDate;
        }
    }

    /**
     * Calculates the fines for overdue books.
     *
     * @param currentDate the current date
     * @return the amount of fines
     */
    public int calculateFines(LocalDate currentDate) {
        if (status == Status.BORROWED && returnDate != null && currentDate.isAfter(returnDate)) {
            long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
            return (int) overdueDays * 1;
        }
        return 0;
    }
}
