package seedu.address.model;

import seedu.address.model.exceptions.AddressBookException;

public abstract class Entity {

    public abstract <T extends Entity> boolean isSame(T other);

    public abstract void notFoundException() throws AddressBookException;

    public abstract void duplicateException() throws AddressBookException;

}
