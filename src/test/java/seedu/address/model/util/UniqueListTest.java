package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBooks.BOOK_A;
import static seedu.address.testutil.TypicalBooks.BOOK_B;
import static seedu.address.testutil.TypicalBooks.BOOK_C;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.book.Book;
import seedu.address.model.exceptions.AddressBookException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UniqueListTest {

    private UniqueList<Person> uniquePersonList;
    private UniqueList<Book> uniqueBookList;

    @BeforeEach
    public void setUp() {
        uniquePersonList = new UniqueList<>();
        uniqueBookList = new UniqueList<>();
    }

    @Test
    public void contains_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_entityNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_entityInList_returnsTrue() throws AddressBookException {
        uniquePersonList.add(ALICE);
        assertTrue(uniquePersonList.contains(ALICE));
    }

    @Test
    public void contains_entityWithSameIdentityFieldsInList_returnsTrue() throws AddressBookException {
        uniquePersonList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicateEntity_throwsAddressBookException() {
        uniquePersonList.add(ALICE);
        assertThrows(AddressBookException.class, () -> uniquePersonList.add(ALICE));
    }

    @Test
    public void set_nullTargetEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.set(null, ALICE));
    }

    @Test
    public void set_entityNotInList_throwsAddressBookException() {
        assertThrows(AddressBookException.class, () -> uniquePersonList.set(ALICE, ALICE));
    }

    @Test
    public void set_entityWithDifferentIdentity_success() throws AddressBookException {
        uniquePersonList.add(ALICE);
        uniquePersonList.set(ALICE, BOB);
        UniqueList<Person> expectedList = new UniqueList<>();
        expectedList.add(BOB);
        assertEquals(expectedList, uniquePersonList);
    }

    @Test
    public void remove_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_entityNotInList_throwsAddressBookException() {
        assertThrows(AddressBookException.class, () -> uniquePersonList.remove(ALICE));
    }

    @Test
    public void remove_existingEntity_removesEntity() throws AddressBookException {
        uniquePersonList.add(ALICE);
        uniquePersonList.remove(ALICE);
        assertFalse(uniquePersonList.contains(ALICE));
    }

    @Test
    public void setList_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePersonList.set((List<Person>) null));
    }

    @Test
    public void setList_withDuplicateEntities_throwsAddressBookException() {
        List<Person> listWithDuplicates = Arrays.asList(ALICE, ALICE);
        assertThrows(AddressBookException.class, () -> uniquePersonList.set(listWithDuplicates));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void uniqueList_withBooks_correctlyHandlesBooks() throws AddressBookException {
        uniqueBookList.add(BOOK_A);
        assertTrue(uniqueBookList.contains(BOOK_A));

        uniqueBookList.add(BOOK_B);
        uniqueBookList.set(BOOK_A, BOOK_C);
        assertFalse(uniqueBookList.contains(BOOK_A));
        assertTrue(uniqueBookList.contains(BOOK_C));
    }

    @Test
    public void iterator_returnsCorrectIterator() throws AddressBookException {
        uniquePersonList.add(ALICE);
        Iterator<Person> iterator = uniquePersonList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ALICE, iterator.next());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(uniquePersonList.equals(uniquePersonList));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(uniquePersonList.equals(1));
    }

    @Test
    public void equals_differentList_returnsFalse() throws AddressBookException {
        uniquePersonList.add(ALICE);
        UniqueList<Person> differentList = new UniqueList<>();
        differentList.add(BOB);
        assertFalse(uniquePersonList.equals(differentList));
    }

    @Test
    public void hashCode_sameList_returnsSameHashCode() throws AddressBookException {
        uniquePersonList.add(ALICE);
        UniqueList<Person> sameList = new UniqueList<>();
        sameList.add(ALICE);
        assertEquals(uniquePersonList.hashCode(), sameList.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() throws AddressBookException {
        uniquePersonList.add(ALICE);
        assertEquals("[" + ALICE.toString() + "]", uniquePersonList.toString());
    }

    @Test
    public void areUnique_listWithDuplicates_throwsAddressBookException() {
        List<Person> listWithDuplicates = Arrays.asList(ALICE, ALICE);
        assertThrows(AddressBookException.class, () -> uniquePersonList.set(listWithDuplicates));
    }
}
