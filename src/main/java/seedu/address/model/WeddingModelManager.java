package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.wedding.Wedding;

/**
 * Represents the in-memory model of the Wedding Planner data.
 * Extends a base ModelManager to inherit standard address book functionality.
 */
public class WeddingModelManager implements WeddingModel {
    private static final Logger logger = LogsCenter.getLogger(WeddingModelManager.class);

    private final WeddingPlanner weddingPlanner;
    private final FilteredList<Wedding> filteredWeddings;
    private final ObservableList<Person> allPersons; // to load wedding members
    private final FilteredList<Person> filteredPersons; // for each wedding
    private Wedding currentWedding;
    private Wedding draftWedding;
    private final UserPrefs userPrefs;

    /**
     * Initializes a WeddingModelManager with the given parameters.
     */
    public WeddingModelManager(ReadOnlyWeddingPlanner weddingPlanner,
                               ReadOnlyUserPrefs userPrefs) {
        requireNonNull(weddingPlanner);
        logger.fine("Initializing with wedding planner: " + weddingPlanner);
        this.userPrefs = new UserPrefs(userPrefs);
        this.weddingPlanner = new WeddingPlanner(weddingPlanner);
        this.filteredWeddings = new FilteredList<>(this.weddingPlanner.getWeddingList());
        this.currentWedding = null;
        this.draftWedding = null;

        // initialized as an empty list
        this.allPersons = FXCollections.observableArrayList();
        this.filteredPersons = new FilteredList<>(allPersons);
    }

    /**
     * Creates an empty WeddingModelManager.
     */
    public WeddingModelManager() {
        this(new WeddingPlanner(), new UserPrefs());
    }


    //=========== UserPrefs =================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }


    @Override
    public Path getWeddingPlannerFilePath() {
        return getUserPrefs().getWeddingPlannerFilePath();
    }

    @Override
    public void setWeddingPlannerFilePath(Path weddingPlannerFilePath) {
        requireNonNull(weddingPlannerFilePath);
        UserPrefs userPrefs = (UserPrefs) getUserPrefs();
        userPrefs.setWeddingPlannerFilePath(weddingPlannerFilePath);
        //getUserPrefs().setWeddingPlannerFilePath(weddingPlannerFilePath);
    }

    @Override
    public void setWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner) {
        this.weddingPlanner.resetData(weddingPlanner);
    }

    @Override
    public ReadOnlyWeddingPlanner getWeddingPlanner() {
        return weddingPlanner;
    }

    // =========== Permanent Storage Operations ===========

    @Override
    public void addWedding(Wedding wedding) {
        weddingPlanner.addWedding(wedding);
        updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddingPlanner.hasWedding(wedding);
    }

    // =========== Draft Management ===========

    @Override
    public void setDraftWedding(Wedding wedding) {
        draftWedding = wedding;
        logger.info("New draft set: " + wedding);
    }

    @Override
    public boolean hasDraftWedding() {
        return draftWedding != null;
    }

    @Override
    public Wedding getDraftWedding() {
        return draftWedding;
    }

    @Override
    public void commitDraftWedding() {
        if (draftWedding == null) {
            throw new IllegalStateException("No draft to commit");
        }
        if (hasWedding(draftWedding)) {
            throw new IllegalStateException("Duplicate wedding in permanent storage");
        }
        addWedding(draftWedding);
        draftWedding = null;
    }

    // =========== Wedding Context Management ===========
    @Override
    public boolean isWeddingPlannerEmpty() {
        return this.weddingPlanner.getWeddingList().isEmpty();
    }

    @Override
    public void setCurrentWedding(Wedding wedding) {
        requireNonNull(wedding);
        currentWedding = wedding;
        logger.info("Entered context for: " + wedding);
        updateFilteredPersonList();
    }

    @Override
    public void clearCurrentWedding() {
        currentWedding = null;
        logger.info("Cleared wedding context");
        updateFilteredPersonList();
    }

    @Override
    public void clearAllWeddings() {
        this.weddingPlanner.resetData(new WeddingPlanner());
        this.filteredWeddings.clear();
        this.currentWedding = null;
        this.draftWedding = null;
        this.allPersons.clear();
        this.filteredPersons.clear();
    }

    @Override
    public boolean hasCurrentWedding() {
        return currentWedding != null;
    }

    @Override
    public Wedding getCurrentWedding() {
        return currentWedding;
    }

    // =========== Person Management ===========

    @Override
    public boolean weddingHasPerson(Wedding wedding, Person person) {
        requireAllNonNull(wedding, person);

        // Check bride
        if (wedding.getBride() != null && wedding.getBride().isSamePerson(person)) {
            return true;
        }

        // Check groom
        if (wedding.getGroom() != null && wedding.getGroom().isSamePerson(person)) {
            return true;
        }

        // Check members
        if (wedding.getMembers() != null) {
            return wedding.getMembers().contains(person);
        }

        return false;
    }

    @Override
    public void addWeddingPerson(Wedding wedding, Person person) {
        requireAllNonNull(wedding, person);

        // Initialize members list if null
        if (wedding.getMembers() == null) {
            wedding.setMembers(new UniquePersonList());
        }

        wedding.getMembers().add(person);
    }

    // =========== Filtered Wedding List Accessors ===========

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return filteredWeddings;
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Updates the internal list with the bride, groom, and other persons associated with the weddings
     * so that it can be displayed.
     */
    public void updateFilteredPersonList() {
        if (currentWedding != null) {
            ObservableList<Person> fullList = FXCollections.observableArrayList();
            fullList.add(currentWedding.getBride());
            fullList.add(currentWedding.getGroom());
            fullList.addAll(currentWedding.getMembers().asUnmodifiableObservableList());
            allPersons.setAll(fullList);
            this.filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        } else if (draftWedding != null) {
            allPersons.setAll(draftWedding.getMembers().asUnmodifiableObservableList());
            this.filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        } else {
            // show an empty list
            allPersons.clear();
            this.filteredPersons.setPredicate(person -> false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof WeddingModelManager)) {
            return false;
        }

        WeddingModelManager other = (WeddingModelManager) obj;
        return weddingPlanner.equals(other.weddingPlanner)
                && filteredWeddings.equals(other.filteredWeddings)
                && java.util.Objects.equals(currentWedding, other.currentWedding)
                && java.util.Objects.equals(draftWedding, other.draftWedding);
    }

    @Override
    public void sortWeddingList() {
        weddingPlanner.sortWeddings();
    }

    /**
     * Deletes the given wedding.
     * The wedding must exist in the Wedding Planner.
     *
     * @param target
     */
    @Override
    public void deleteWedding(Wedding target) {
        weddingPlanner.deleteWedding(target);

        // Check if the deleted wedding is the current wedding
        if (currentWedding != null && currentWedding.equals(target)) {
            clearCurrentWedding();
        }
    }

    /**
     * Removes a person from a wedding's list of members.
     * Adjusts the index to account for the bride and groom in the GUI representation.
     * @param wedding The wedding from which the person is to be removed.
     * @param indexToRemove The 1-based index of the person in the displayed list.
     */
    @Override
    public void removeWeddingPerson(Wedding wedding, int indexToRemove) {
        requireAllNonNull(wedding);
        // account for bride and groom in GUI and 0-based indexing
        indexToRemove = indexToRemove - 3;
        wedding.getMembers().removeByIndex(indexToRemove);
    }

}
