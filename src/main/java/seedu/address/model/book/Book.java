package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Entity;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.exceptions.LibraryException;
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
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both Books have the same name.
     * This defines a weaker notion of equality between two Books.
     */
    @Override
    public <T extends Entity> boolean isSame(T other) {
        return this.equals(other);
    }

    @Override
    public void notFoundException() throws CommandException {
        throw new BookNotFoundException(name);
    }

    @Override
    public void duplicateException() throws CommandException {
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
        }
        else if (other == this) {
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
