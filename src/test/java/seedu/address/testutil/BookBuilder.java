package seedu.address.testutil;

import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookStatus;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {
    public static final String DEFAULT_TITLE = "Harry Potter";
    private BookName bookname;
    private BookStatus status;
    private Set<Tag> tags;
    /**
     * Creates a {@code BookBuilder} with the default details.
     */
    public BookBuilder() {
        bookname = new BookName(DEFAULT_TITLE);
        status = new BookStatus();
        tags = new HashSet<>();
    }
    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        bookname = bookToCopy.getName();
        status = bookToCopy.getStatus();
        tags = new HashSet<>();
    }
    /**
     * Sets the {@code BookName} of the {@code Book} that we are building.
     */
    public BookBuilder withName(String name) {
        this.bookname = new BookName(name);
        return this;
    }
    /**
     * Sets the {@code BookStatus} of the {@code Book} that we are building.
     */
    public BookBuilder withStatus(BookStatus status) {
        this.status = status;
        return this;
    }
    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }
    public Book build() {
        return new Book(bookname, tags);
    }
}
