package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;

public class UserMatchesPredicateTest {
    @Test
    public void test_borrowedBookMatches_trueWhenBookNameMatches() {
        Person person = new Person(new Name("Test"), new Phone("999"),
                new Email("x@x.com"), new Address("x"), Membership.ACTIVE, new HashSet<>());
        person.getBorrowedBooks().add(new Book(new BookName("Percy Jackson"), new HashSet<>()));
        var predicate = new UserMatchesPredicate(Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of("Percy Jackson"), Optional.empty());
        assertTrue(predicate.test(person));
    }
}
