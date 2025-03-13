package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Book;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Book}.
 */
class JsonAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBook} with the given Book details.
     */
    @JsonCreator
    public JsonAdaptedBook(@JsonProperty("name") String name,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedBook(Book source) {
        name = source.getName().bookName;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Book object into the model's {@code Book} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Book.
     */
    public Book toModelType() throws IllegalValueException {
        final List<Tag> BookTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            BookTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, BookName.class.getSimpleName()));
        }
        if (!BookName.isValidName(name)) {
            throw new IllegalValueException(BookName.MESSAGE_CONSTRAINTS);
        }
        final BookName modelName = new BookName(name);
        final Set<Tag> modelTags = new HashSet<>(BookTags);
        return new Book(modelName, modelTags);
    }

}
