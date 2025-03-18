package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's name in the library.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class BookName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String bookName;

    /**
     * Constructs a {@code Name}.
     *
     * @param bookName A valid name.
     */
    public BookName(String bookName) {
        requireNonNull(bookName);
        checkArgument(isValidName(bookName), MESSAGE_CONSTRAINTS);
        this.bookName = bookName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return bookName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookName)) {
            return false;
        }

        BookName otherName = (BookName) other;
        return bookName.equals(otherName.bookName);
    }

    @Override
    public int hashCode() {
        return bookName.hashCode();
    }

}
