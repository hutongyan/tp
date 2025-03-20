package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BookNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BookName(null));
    }

    @Test
    public void constructor_invalidBookName_throwsIllegalArgumentException() {
        String invalidBookName = "";
        assertThrows(IllegalArgumentException.class, () -> new BookName(invalidBookName));
    }

    @Test
    public void isValidBookName() {
        // null book name
        assertThrows(NullPointerException.class, () -> BookName.isValidName(null));

        // invalid book name
        assertFalse(BookName.isValidName("")); // empty string
        assertFalse(BookName.isValidName(" ")); // spaces only
        assertFalse(BookName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(BookName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid book name
        assertTrue(BookName.isValidName("peter jack")); // alphabets only
        assertTrue(BookName.isValidName("12345")); // numbers only
        assertTrue(BookName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(BookName.isValidName("Capital Tan")); // with capital letters
        assertTrue(BookName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        BookName bookName = new BookName("Valid Book Name");

        // same values -> returns true
        assertTrue(bookName.equals(new BookName("Valid Book Name")));

        // same object -> returns true
        assertTrue(bookName.equals(bookName));

        // null -> returns false
        assertFalse(bookName.equals(null));

        // different types -> returns false
        assertFalse(bookName.equals(5.0f));

        // different values -> returns false
        assertFalse(bookName.equals(new BookName("Other Valid Book Name")));
    }
}

