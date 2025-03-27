package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_A;
import static seedu.address.testutil.TypicalBooks.BOOK_B;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;



public class BookTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Book book = new BookBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> book.getTags().remove(0));
    }

    @Test
    public void isSameBook() {
        // same object -> returns true
        assertTrue(BOOK_A.isSame(BOOK_A));

        // null -> returns false
        assertFalse(BOOK_A.isSame(null));

        //same bookname, all other attributes different -> returns true
        Book editedBookA = new BookBuilder(BOOK_A).withTags("newTag").build();
        assertTrue(BOOK_A.isSame(editedBookA));

        //same tag, all other attributes different -> returns false
        editedBookA = new BookBuilder(BOOK_A).withName("newName").build();
        assertFalse(BOOK_A.isSame(editedBookA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Book bookACopy = new BookBuilder(BOOK_A).build();
        assertTrue(BOOK_A.equals(bookACopy));

        // same object -> returns true
        assertTrue(BOOK_A.equals(BOOK_A));

        // null -> returns false
        assertFalse(BOOK_A.equals(null));

        // different type -> returns false
        assertFalse(BOOK_A.equals(5));

        // different book -> returns false
        assertFalse(BOOK_A.equals(BOOK_B));

        // different name -> returns false
        Book editedBookA = new BookBuilder(BOOK_A).withName("newName").build();
        assertFalse(BOOK_A.equals(editedBookA));

        // different tags -> returns false
        editedBookA = new BookBuilder(BOOK_A).withTags("newTag").build();
        assertFalse(BOOK_A.equals(editedBookA));
    }
}
