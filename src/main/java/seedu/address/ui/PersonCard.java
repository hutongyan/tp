package seedu.address.ui;

import java.time.LocalDate;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.book.Book;
import seedu.address.model.person.Membership;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label membership;
    @FXML
    private Label bookInfo;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        Membership status = person.getMembership();
        membership.setText(status.toString());
        switch (status) {
        case ACTIVE -> membership.getStyleClass().add("membership-active");
        case EXPIRED -> membership.getStyleClass().add("membership-expired");
        case NONMEMBER -> membership.getStyleClass().add("membership-nonmember");
        default -> membership.getStyleClass().add("membership-unknown");
        }

        membership.getStyleClass().add("membership-label");

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("tag-bubble");
                    tags.getChildren().add(tagLabel);
                });

        // Issued book info
        StringBuilder bookText = new StringBuilder();
        for (Book book : person.getBorrowedBooks()) {
            var bookStatus = book.getStatus();
            String bookName = book.getName().toString();
            LocalDate due = bookStatus.getReturnDate();
            int fine = bookStatus.calculateFines(LocalDate.now());
            if (fine > 0) {
                bookText.append("⚠ Issued book: ").append(bookName)
                        .append(" (overdue, was due on: ").append(due).append(") - $")
                        .append(fine).append(" fine\n");
                bookInfo.getStyleClass().add("book-info-overdue");
            } else {
                bookText.append("📚 Issued book: ").append(bookName)
                        .append("; due: ").append(due).append("\n");
            }
        }

        if (bookText.length() > 0) {
            bookInfo.setText(bookText.toString().trim());
        } else {
            bookInfo.setText("");
        }
    }

    public Label getNameLabel() {
        return name;
    }

    public Label getPhoneLabel() {
        return phone;
    }

    public Label getEmailLabel() {
        return email;
    }

    public Label getAddressLabel() {
        return address;
    }

    public Label getMembershipLabel() {
        return membership;
    }

    public FlowPane getTagsPane() {
        return tags;
    }

    public Label getIdLabel() {
        return id;
    }

    public Label getBookInfoLabel() {
        return bookInfo;
    }
}
