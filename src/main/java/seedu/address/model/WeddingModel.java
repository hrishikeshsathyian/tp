package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * The API of the Wedding Model component.
 */
public interface WeddingModel {
    /**
     * Adds the given wedding.
     * {@code Wedding} must not already exist in the Wedding Planner.
     */
    void addWedding(Wedding wedding);

    /**
     * Returns true if a wedding with the same identity as {@code Wedding} exists in the Wedding Planner.
     */
    boolean hasWedding(Wedding wedding);

    /**
     * Checks for duplicate Person in {@code wedding}.
     * @param wedding
     * @param toAdd
     * @return true if the Wedding already has a Person with the same identity as {@code Person}
     */
    boolean weddingHasPerson(Wedding wedding, Person toAdd);

    /**
     * Adds a Person to the given Wedding.
     * @param wedding
     * @param toAdd
     */
    void addWeddingPerson(Wedding wedding, Person toAdd);

    /** Returns an unmodifiable view of the wedding list */
    ObservableList<Wedding> getWeddingList();
}
