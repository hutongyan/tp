package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import seedu.address.model.book.exceptions.BookNotBorrowedException;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.book.exceptions.DifferentBorrowerException;
import seedu.address.model.person.Membership;
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
    private Person borrower;
    private LocalDate issueDate;
    private LocalDate returnDate;

    /**
     * Constructs a {@code BookStatus} with default values.
     */
    public BookStatus() {
        this.status = Status.AVAILABLE;
        this.borrower = null;
        this.issueDate = null;
        this.returnDate = null;
    }

    /**
     * Issues the book to a member.
     *
     * @param issueDate the date the book is issued
     * @param borrower the member to whom the book is issued
     * @throws BookUnavailableException if the book is already borrowed
     */
    public void issueBook(LocalDate issueDate, Person borrower) throws BookUnavailableException {
        requireNonNull(issueDate);
        requireNonNull(borrower);
        if (status == Status.BORROWED) {
            throw new BookUnavailableException(checkStatus());
        }
        this.status = Status.BORROWED;
        this.issueDate = issueDate;
        this.returnDate = issueDate.plusDays(14);
        this.borrower = borrower;
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
        this.borrower = null;
    }

    /**
     * Extends the duration for which the book can be borrowed without paying overdue fees
     *
     * @param issueDate
     * @param borrower
     */
    public void extendBook(LocalDate issueDate, Person borrower) throws BookNotBorrowedException,
            DifferentBorrowerException {
        requireNonNull(issueDate);
        requireNonNull(borrower);

        if (status == Status.AVAILABLE) {
            throw new BookNotBorrowedException("The given book has not been issued yet");
        }
        if (borrower != this.borrower) {
            throw new DifferentBorrowerException("This person has not borrowed this book");
        }

        this.status = Status.BORROWED;
        this.issueDate = issueDate;
        this.returnDate = issueDate.plusDays(14);
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
            return "Currently borrowed by " + borrower.getName()
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
        boolean isOverdue = status == Status.BORROWED && currentDate.isAfter(returnDate);
        if (isOverdue) {
            boolean isMember = borrower.getMembership() == Membership.ACTIVE;
            long fines = isMember ? 1 : 2;
            long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
            return (int) (overdueDays * fines);
        }
        return 0;
    }
    /**
     * Returns the status of the book.
     *
     * @return the status of the book
     */
    public Status getStatus() {
        return status;
    }
    /**
     * Returns the issue date of the book.
     *
     * @return the issue date of the book
     */
    public LocalDate getIssueDate() {
        return issueDate;
    }
    /**
     * Returns the return date of the book.
     *
     * @return the return date of the book
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }
    /**
     * Returns the member who borrowed the book.
     *
     * @return the member who borrowed the book
     */
    public Person getBorrower() {
        return borrower;
    }
}
