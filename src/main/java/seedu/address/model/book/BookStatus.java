package seedu.address.model.book;

import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.person.Person;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookStatus {
    public enum Status {
        AVAILABLE, BORROWED
    }

    private Status status;
    private Person borrowedMember;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public BookStatus() {
        this.status = Status.AVAILABLE;
        this.borrowedMember = null;
        this.issueDate = null;
        this.returnDate = null;
    }

    public void issueBook(LocalDate issueDate, Person member) throws RuntimeException { // For issue command to be implemented later.
        if (status == Status.BORROWED) {
            throw new BookUnavailableException(checkStatus());
        }
        this.status = Status.BORROWED;
        this.issueDate = issueDate;
        this.returnDate = issueDate.plusDays(14);
        this.borrowedMember = member;
    }

    public void returnBook() throws RuntimeException { // For return command to be implemented later.
        if (status == Status.AVAILABLE) {
            throw new BookUnavailableException(checkStatus());
        }
        this.status = Status.AVAILABLE;
        this.issueDate = null;
        this.returnDate = null;
        this.borrowedMember = null;
    }

    public String checkStatus() {
        if (status == Status.AVAILABLE) {
            return "Available";
        } else {
            return "Currently borrowed by " + borrowedMember.getName() + 
            " from " + issueDate + " till " + returnDate;
        }
    }

    public int calculateFines(LocalDate currentDate) { // For check fines command to be implemented later.
        if (status == Status.BORROWED && returnDate != null && currentDate.isAfter(returnDate)) {
            long overdueDays = ChronoUnit.DAYS.between(returnDate, currentDate);
            return (int) overdueDays * 1; 
        }
        return 0;
    }

}
