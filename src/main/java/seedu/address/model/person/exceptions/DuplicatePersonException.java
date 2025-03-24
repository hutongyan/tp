package seedu.address.model.person.exceptions;

import seedu.address.model.exceptions.AddressBookException;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePersonException extends AddressBookException {
    public DuplicatePersonException() {
        super("This person already exists in the address book");
    }
}
