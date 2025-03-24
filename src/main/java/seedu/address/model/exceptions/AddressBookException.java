package seedu.address.model.exceptions;

/**
 * Represents a generic exception for the Address Book application.
 * This serves as a base exception for more specific exceptions
 * related to entity management in the address book.
 */
public class AddressBookException extends RuntimeException {

    /**
     * Constructs a new {@code AddressBookException} with the specified detail message.
     *
     * @param msg The detail message describing the exception.
     */
    public AddressBookException(String msg) {
        super(msg);
    }
}
