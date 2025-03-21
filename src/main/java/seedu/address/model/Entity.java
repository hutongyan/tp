package seedu.address.model;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.LibraryException;

public abstract class Entity {

    public abstract <T extends Entity> boolean isSame(T other);

    public abstract void notFoundException() throws CommandException;

    public abstract void duplicateException() throws CommandException;

}
