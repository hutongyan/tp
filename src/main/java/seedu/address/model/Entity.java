package seedu.address.model;

import seedu.address.model.exceptions.AddressBookException;

/**
 * Represents an abstract entity in the address book application.
 * This serves as a base class for all entities that require
 * identification and exception handling.
 */
public abstract class Entity {

    /**
     * Checks if this entity is the same as another entity.
     * @param other The other entity to compare with.
     * @return true if both entities are considered the same, false otherwise.
     */
    public abstract boolean isSame(Entity other);

    /**
     * Throws an exception indicating that the entity was not found.
     *
     * @throws AddressBookException if the entity is not found.
     */
    public abstract void notFoundException() throws AddressBookException;

    /**
     * Throws an exception indicating that a duplicate entity exists.
     *
     * @throws AddressBookException if a duplicate entity is detected.
     */
    public abstract void duplicateException() throws AddressBookException;
}
