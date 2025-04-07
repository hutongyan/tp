package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Entity;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.exceptions.AddressBookException;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * Represents a book in the library.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book extends Entity {

    // Identity fields
    private final BookName name;
    private final BookStatus status;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Book(BookName name, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.tags.addAll(tags);
        this.status = new BookStatus();
    }

    public BookName getName() {
        return name;
    }

    public BookStatus getStatus() {
        return status;
    }

    /**
     * Checks and returns the current status of the book.
     *
     * @return A string representing the current status of the book.
     */
    public String checkStatus() {
        return status.checkStatus();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if the book is issued.
     */
    public boolean isIssued() {
        return status.getStatus() == BookStatus.Status.BORROWED;
    }

    /**
     * Issue the book to a person.
     */
    public void issueBook(LocalDate localDate, Person person) throws BookUnavailableException {
        status.issueBook(localDate, person);
    }

    /**
     * Return the book to the library.
     */
    public int returnBook(LocalDate localDate) throws BookUnavailableException {
        if (localDate.isBefore(status.getIssueDate())) {
            throw new BookUnavailableException("Date is before today.");
        }
        int fine = status.calculateFines(localDate);
        status.returnBook();
        return fine;
    }

    /**
     * Extends the duration for which the book can be borrowed without paying overdue fees
     */
    public void extendBook(Person personToExtend) {
        status.extendBook(personToExtend);
    }

    /**
     * Returns the person who has borrowed the book.
     *
     * @return The person who has borrowed the book.
     */
    public Person getBorrower() {
        return status.getBorrower();
    }

    /**
     * Returns true if both Books have the same name.
     * This defines a weaker notion of equality between two Books.
     */
    @Override
    public boolean isSame(Entity other) {
        if (this == other) {
            return true;
        } else if (other instanceof Book otherBook) {
            return name.equals(otherBook.name);
        } else {
            return false;
        }
    }

    @Override
    public void notFoundException() throws AddressBookException {
        throw new BookNotFoundException(name);
    }

    @Override
    public void duplicateException() throws AddressBookException {
        throw new DuplicateBookException(this);
    }

    /**
     * Returns true if both Books have the same identity and data fields.
     * This defines a stronger notion of equality between two Books.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Book otherBook)) {
            return false;
        } else if (other == this) {
            return true;
        }
        return name.equals(otherBook.name)
                && tags.equals(otherBook.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("tags", tags)
                .toString();
    }

}
