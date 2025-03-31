package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

/**
 * A list of weddings that enforces uniqueness between its elements.
 */
public class UniqueWeddingList implements Iterable<Wedding> {
    private final ObservableList<Wedding> internalList = FXCollections.observableArrayList();
    private final ObservableList<Wedding> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent wedding.
     */
    public boolean contains(Wedding toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWedding);
    }

    /**
     * Adds a wedding to the list.
     * @throws DuplicateWeddingException if duplicate exists
     */
    public void add(Wedding toAdd) throws DuplicateWeddingException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateWeddingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent wedding from the list.
     * @throws WeddingNotFoundException if not found
     */
    public void remove(Wedding toRemove) throws WeddingNotFoundException {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WeddingNotFoundException();
        }
    }

    /**
     * Replaces the contents with {@code weddings}
     */
    public void setWeddings(List<Wedding> weddings) {
        requireAllNonNull(weddings);
        if (!weddingsAreUnique(weddings)) {
            throw new DuplicateWeddingException();
        }
        internalList.setAll(weddings);
    }

    public void sort() {
        internalList.sort(Comparator.comparing(wedding -> ((Wedding) wedding).getDate()));
    }


    /**
     * Returns unmodifiable view of the list
     */
    public ObservableList<Wedding> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Wedding> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueWeddingList
                && internalList.equals(((UniqueWeddingList) other).internalList));
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
     * Returns true if all weddings are unique
     */
    private boolean weddingsAreUnique(List<Wedding> weddings) {
        for (int i = 0; i < weddings.size() - 1; i++) {
            for (int j = i + 1; j < weddings.size(); j++) {
                if (weddings.get(i).isSameWedding(weddings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
    //@Override
    //public String toString() {
    //return asUnmodifiableObservableList().toString();
    //}

    /**
     * Deletes a wedding from the wedding list.
     * @param toRemove The wedding to be deleted.
     */
    public void deleteWedding(Wedding toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new WeddingNotFoundException();
        }
    }

}
