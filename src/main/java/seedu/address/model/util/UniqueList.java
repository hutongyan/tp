package seedu.address.model.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Entity;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueList<T extends Entity> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent entity as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }


    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(T toAdd) throws CommandException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            toAdd.duplicateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedT}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedT} must not be the same as another existing person in the list.
     */
    public void set(T target, T edited) throws CommandException {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            target.notFoundException();
        }

        if (!target.isSame(edited) && contains(edited)) {
            target.duplicateException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(T toRemove) throws CommandException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            toRemove.notFoundException();
        }
    }

    public void set(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void set(List<T> list) {
        requireAllNonNull(list);
        areUnique(list);
        internalList.setAll(list);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueList<?> otherUniqueTList)) {
            return false;
        }

        return internalList.equals(otherUniqueTList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean areUnique(List<T> lst) throws CommandException {
        for (int i = 0; i < lst.size() - 1; i++) {
            T curr = lst.get(i);
            for (int j = i + 1; j < lst.size(); j++) {
                if (lst.get(i).isSame(lst.get(j))) {
                    curr.duplicateException();
                }
            }
        }
        return true;
    }
}
