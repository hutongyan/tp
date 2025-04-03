package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.GraphicsEnvironment;
import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    @BeforeAll
    public static void setup() {
        assumeTrue(!GraphicsEnvironment.isHeadless(), "Skipping test in headless environment");
        JavaFxInitializer.init();
    }

    @Test
    public void display_personWithTagsAndMembership_displaysAllFieldsCorrectly() {
        Person person = new PersonBuilder()
                .withName("John Doe")
                .withPhone("99999999")
                .withEmail("john@example.com")
                .withAddress("123 Main St")
                .withMembership("EXPIRED")
                .withTags("cool", "vip")
                .build();

        PersonCard personCard = new PersonCard(person, 1);

        assertNotNull(personCard.getRoot());

        assertEquals("John Doe", personCard.getNameLabel().getText());
        assertEquals("99999999", personCard.getPhoneLabel().getText());
        assertEquals("john@example.com", personCard.getEmailLabel().getText());
        assertEquals("123 Main St", personCard.getAddressLabel().getText());
        assertEquals("EXPIRED", personCard.getMembershipLabel().getText());
        assertEquals("1. ", personCard.getIdLabel().getText());
        assertEquals(2, personCard.getTagsPane().getChildren().size());
    }

    @Test
    public void display_personWithoutTags_displaysFieldsCorrectly() {
        Person person = new PersonBuilder()
                .withName("Jane Smith")
                .withPhone("88888888")
                .withEmail("jane@example.com")
                .withAddress("456 Another St")
                .withMembership("ACTIVE")
                .withTags() // no tags
                .build();

        PersonCard personCard = new PersonCard(person, 2);

        assertNotNull(personCard.getRoot());

        assertEquals("Jane Smith", personCard.getNameLabel().getText());
        assertEquals("88888888", personCard.getPhoneLabel().getText());
        assertEquals("jane@example.com", personCard.getEmailLabel().getText());
        assertEquals("456 Another St", personCard.getAddressLabel().getText());
        assertEquals("ACTIVE", personCard.getMembershipLabel().getText());
        assertEquals("2. ", personCard.getIdLabel().getText());
        assertEquals(0, personCard.getTagsPane().getChildren().size());
    }

    @Test
    public void display_personWithBorrowedBook_showsBookInfo() throws Exception {
        Person person = new PersonBuilder()
                .withName("Book Reader")
                .withMembership("ACTIVE")
                .withEmail("borrower@example.com")
                .build();

        Book book = new Book(new BookName("Test Book"), new HashSet<>());

        book.getStatus().issueBook(LocalDate.now().minusDays(20), person);

        person.getBorrowedBooks().add(book);

        PersonCard personCard = new PersonCard(person, 4);
        Label bookInfo = personCard.getBookInfoLabel();

        assertNotNull(bookInfo);
        String text = bookInfo.getText();

        assertEquals(true, text.contains("Test Book"));
        assertEquals(true, text.toLowerCase().contains("overdue"));
    }


    @Test
    public void equals_sameObject_returnsTrue() {
        Person person = new PersonBuilder().withName("Sam").build();
        PersonCard card = new PersonCard(person, 3);
        assertEquals(card, card);
    }

    @Test
    public void equals_differentObjectSameData_returnsFalse() {
        Person person1 = new PersonBuilder().withName("Sam").build();
        Person person2 = new PersonBuilder().withName("Sam").build();
        PersonCard card1 = new PersonCard(person1, 1);
        PersonCard card2 = new PersonCard(person2, 1);
        assertEquals(false, card1.equals(card2));
    }
}
