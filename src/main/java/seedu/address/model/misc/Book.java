package seedu.address.model.misc;

/**
 * Represents a book with a name.
 */
public class Book {
    /** The name of the book. */
    private final String name;

    /**
     * Constructs a {@code Book} with the given name.
     *
     * @param name The name of the book.
     */
    public Book(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the book, which is its name.
     *
     * @return The name of the book.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
