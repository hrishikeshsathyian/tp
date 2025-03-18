package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * The API of the Wedding Model component.
 */
public interface WeddingModel {
    // =========== Permanent Storage Operations ===========
    /**
     * Commits the draft wedding to permanent storage.
     * {@code Wedding} must not already exist in the Wedding Planner.
     */
    void addWedding(Wedding wedding);

    /**
     * Returns true if a wedding with the same identity exists in permanent storage.
     */
    boolean hasWedding(Wedding wedding);

    // =========== Draft Management ===========
    /**
     * Stores a wedding draft temporarily before commitment
     */
    void setDraftWedding(Wedding wedding);

    /**
     * Returns true if there's an existing draft wedding
     */
    boolean hasDraftWedding();

    /**
     * Gets the current draft wedding
     */
    Wedding getDraftWedding();

    /**
     * Moves draft wedding to permanent storage
     */
    void commitDraftWedding();

    // =========== Wedding Context Management ===========
    /**
     * Enters a wedding context for editing
     */
    void setCurrentWedding(Wedding wedding);

    /**
     * Exits the current wedding context
     */
    void clearCurrentWedding();

    /**
     * Returns true if a wedding context is active
     */
    boolean hasCurrentWedding();

    /**
     * Gets the currently open wedding
     */
    Wedding getCurrentWedding();

    // =========== Person Management ===========
    /**
     * Checks for duplicate Person in a wedding
     */
    boolean weddingHasPerson(Wedding wedding, Person toAdd);

    /**
     * Adds a Person to the specified wedding
     */
    void addWeddingPerson(Wedding wedding, Person toAdd);

    /**
     * Returns an unmodifiable view of the filtered wedding list
     */
    ObservableList<Wedding> getFilteredWeddingList();

    /**
     * Updates the filter of the filtered wedding list to filter by the given {@code predicate}.
     * @throws NullPointerException if the predicate is null.
     */
    void updateFilteredWeddingList(Predicate<Wedding> predicate);
}
