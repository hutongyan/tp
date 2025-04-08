package seedu.address.model.person;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Represents a predicate that tests whether a {@code Person} matches the given filter criteria.
 */
public class UserMatchesPredicate implements Predicate<Person> {
    private final Optional<String> email;
    private final Optional<String> name;
    private final Optional<String> membership;
    private final Optional<String> borrowedBook;
    private final Optional<String> tag;

    /**
     * Constructs a {@code UserMatchesPredicate} with the specified filters.
     *
     * @param email The email filter (if provided).
     * @param name The name filter (if provided).
     * @param membership The membership filter (if provided).
     * @param borrowedBook The borrowed book filter (if provided).
     * @param tag The tag filter (if provided).
     */
    public UserMatchesPredicate(Optional<String> email, Optional<String> name, Optional<String> membership,
                                Optional<String> borrowedBook, Optional<String> tag) {
        this.email = email;
        this.name = name;
        this.membership = membership;
        this.borrowedBook = borrowedBook;
        this.tag = tag;
    }

    /**
     * Tests if a given person matches the provided filter criteria.
     *
     * @param person The person to test.
     * @return True if the person matches the criteria, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        boolean emailMatches = email.map(e ->
                person.getEmail().toString().equalsIgnoreCase(e)).orElse(true);
        boolean nameMatches = name.map(n ->
                person.getName().fullName.toLowerCase().contains(n.toLowerCase())).orElse(true);
        boolean membershipMatches = membership.map(m ->
                person.getMembership().toString().equalsIgnoreCase(m)).orElse(true);
        boolean borrowedBookMatches = !borrowedBook.isPresent()
                                      || person.getBorrowedBooks().containsField(
                                              borrowedBook.get(), b -> b.getName().toString());
        boolean tagMatches = tag.map(t ->
                person.getTags().stream().anyMatch(tagObj -> tagObj.tagName.equalsIgnoreCase(t))).orElse(true);

        return emailMatches && nameMatches && membershipMatches && borrowedBookMatches && tagMatches;
    }
}
