package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book BOOK_A = new Book(new BookName("Introduction to Java"), new HashSet<>());
    public static final Book BOOK_B = new Book(new BookName("Data Structures and Algorithms"), new HashSet<>());
    public static final Book BOOK_C = new Book(new BookName("Machine Learning Basics"), new HashSet<>());

    private TypicalBooks() {}

    /**
     * Returns an {@code AddressBook} with all the typical books.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Book book : getTypicalBooks()) {
            ab.addBook(book);
        }
        return ab;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(BOOK_A, BOOK_B, BOOK_C));
    }
}

