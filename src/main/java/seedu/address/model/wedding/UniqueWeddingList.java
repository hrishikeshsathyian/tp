package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

/**
 * A list of weddings that enforces uniqueness between its elements and does not allow nulls.
 * A wedding is considered unique by comparing using {@code Wedding#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueWeddingList implements Iterable<Wedding>{
    private final ObservableList<Wedding> internalWeddingsList = FXCollections.observableArrayList();
    private final ObservableList<Wedding> internalUnmodifiableWeddingsList = FXCollections.unmodifiableObservableList(internalWeddingsList);

    /**
     * Returns true if the list contains an equivalent wedding as the given argument.
     */
    public boolean contains(Wedding toCheck) {
        requireNonNull(toCheck);
        return internalWeddingsList.stream().anyMatch(toCheck::isSameWedding);
    }

    /**
     * Adds a wedding to the list.
     * The wedding must not already exist in the list.
     */
    public void add(Wedding toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWeddingException();
        }
        internalWeddingsList.add(toAdd);
    }

    /**
     * Removes the equivalent wedding from the list.
     * The wedding must exist in the list.
     */
    public void remove(Wedding toRemove) {
        requireNonNull(toRemove);
        if (!internalWeddingsList.remove(toRemove)) {
            throw new WeddingNotFoundException();
        }
    }

    /**
     * Replaces the list of weddings with a new specified list of weddings
     * @param replacement
     */
    public void setWeddings(UniqueWeddingList replacement) {
        requireNonNull(replacement);
        internalWeddingsList.setAll(replacement.internalWeddingsList);
    }

    public ObservableList<Wedding> getWeddingList() {
        return internalWeddingsList;
    }

    @Override
    public Iterator<Wedding> iterator() {
        return internalWeddingsList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniqueWeddingList otherUniqueWeddingList = (UniqueWeddingList) other;
        return internalWeddingsList.equals(otherUniqueWeddingList.internalWeddingsList);
    }

    @Override
    public int hashCode() {
        return internalWeddingsList.hashCode();
    }

    @Override
    public String toString() {
        return internalWeddingsList.toString();
    }
}
