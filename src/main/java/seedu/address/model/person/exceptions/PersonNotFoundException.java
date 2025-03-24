package seedu.address.model.person.exceptions;

import seedu.address.model.exceptions.AddressBookException;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends AddressBookException {
    public PersonNotFoundException() {
        super("The person index provided is invalid");
    }
}
