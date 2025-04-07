package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Entity;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.BookUnavailableException;
import seedu.address.model.exceptions.AddressBookException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.UniqueList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person extends Entity {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Membership membership;
    private final Set<Tag> tags = new HashSet<>();
    private UniqueList<Book> books = new UniqueList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Membership membership, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.membership = membership;
        assert email != null : "Email should not be null";
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Membership getMembership() {
        return membership;
    }

    /**
     * Adds the specified book to the list of books borrowed by the person.
     *
     * @param book The book to be borrowed.
     */
    public void borrows(Book book) {
        this.books.add(book);
    }

    /**
     * Removes the specified book from the list of books borrowed by the person.
     *
     * @param book The book to be borrowed.
     */
    public void returns(Book book) throws BookUnavailableException {
        requireAllNonNull(book);
        try {
            this.books.remove(book);
        } catch (AddressBookException e) {
            throw new BookUnavailableException("Book not found in the borrowed list");
        }
    }

    /**
     * Checks if the person has borrowed the specified book.
     *
     * @param book The book to check.
     * @return true if the person has borrowed the book, false otherwise.
     */
    public boolean hasBorrowed(Book book) {
        requireAllNonNull(book);
        return this.books.contains(book);
    }

    /**
     * Returns the list of books borrowed by the person.
     *
     * @return A UniqueList of books borrowed by the person.
     */
    public UniqueList<Book> getBorrowedBooks() {
        return books;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    @Override
    public boolean isSame(Entity other) {
        if (!(other instanceof Person otherPerson)) {
            return false;
        } else if (other == this) {
            return true;
        }
        return email.equals(otherPerson.email);
    }

    @Override
    public void notFoundException() throws AddressBookException {
        throw new PersonNotFoundException();
    }

    @Override
    public void duplicateException() throws AddressBookException {
        throw new DuplicatePersonException();
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && membership.equals(otherPerson.membership)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("membership", membership)
                .add("tags", tags)
                .toString();
    }

}
