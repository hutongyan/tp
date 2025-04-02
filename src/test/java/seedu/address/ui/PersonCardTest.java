package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class PersonCardTest {

    @BeforeAll
    public static void setup() {
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

        Label name = personCard.getNameLabel();
        Label phone = personCard.getPhoneLabel();
        Label email = personCard.getEmailLabel();
        Label address = personCard.getAddressLabel();
        Label membership = personCard.getMembershipLabel();
        FlowPane tags = personCard.getTagsPane();

        assertEquals("John Doe", name.getText());
        assertEquals("99999999", phone.getText());
        assertEquals("john@example.com", email.getText());
        assertEquals("123 Main St", address.getText());
        assertEquals("EXPIRED", membership.getText());
        assertEquals(2, tags.getChildren().size());
    }

    @Test
    public void display_personWithoutTags_displaysFieldsCorrectly() {
        Person person = new PersonBuilder()
                .withName("Jane Smith")
                .withPhone("88888888")
                .withEmail("jane@example.com")
                .withAddress("456 Another St")
                .withMembership("ACTIVE")
                .build();

        PersonCard personCard = new PersonCard(person, 2);

        assertNotNull(personCard.getRoot());

        Label name = personCard.getNameLabel();
        Label phone = personCard.getPhoneLabel();
        Label email = personCard.getEmailLabel();
        Label address = personCard.getAddressLabel();
        Label membership = personCard.getMembershipLabel();
        FlowPane tags = personCard.getTagsPane();

        assertEquals("Jane Smith", name.getText());
        assertEquals("88888888", phone.getText());
        assertEquals("jane@example.com", email.getText());
        assertEquals("456 Another St", address.getText());
        assertEquals("ACTIVE", membership.getText());
        assertEquals(0, tags.getChildren().size());
    }
}
