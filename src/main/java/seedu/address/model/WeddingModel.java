package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * The API of the Wedding Model component which extends the base Model.
 */
public interface WeddingModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Wedding> PREDICATE_SHOW_ALL_WEDDINGS = unused -> true;
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the wedding planner file path.
     */
    Path getWeddingPlannerFilePath();

    /**
     * Sets the wedding planner file path.
     */
    void setWeddingPlannerFilePath(Path weddingPlannerFilePath);

    /**
     * Replaces wedding planner data with the data in {@code weddingPlanner}.
     */
    void setWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner);

    /**
     * Returns the wedding planner.
     */
    ReadOnlyWeddingPlanner getWeddingPlanner();

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

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
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
    void addWeddingPerson(Wedding wedding, Person person);

    /**
     * Returns an unmodifiable view of the filtered wedding list
     */
    ObservableList<Wedding> getFilteredWeddingList();

    /**
     * Returns an unmodifiable view of the filtered member list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered wedding list to filter by the given {@code predicate}.
     * @throws NullPointerException if the predicate is null.
     */
    void updateFilteredWeddingList(Predicate<Wedding> predicate);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if the predicate is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sorts all weddings by date.
     */
    void sortWeddingList();

    /**
     * Deletes the given wedding.
     * The wedding must exist in the Wedding Planner.
     */
    void deleteWedding(Wedding target);

    /**
     * Removes Person from wedding.
     * The index must be valid.
     */
    void removeWeddingPerson(Wedding wedding, int indexToRemove);
}
