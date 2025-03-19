package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.wedding.Wedding;

/**
 * Represents the in-memory model of the Wedding Planner data.
 * Extends a base ModelManager to inherit standard address book functionality.
 */
public class WeddingModelManager extends ModelManager implements WeddingModel {
    private static final Logger logger = LogsCenter.getLogger(WeddingModelManager.class);

    private final WeddingPlanner weddingPlanner;
    private final FilteredList<Wedding> filteredWeddings;
    private Wedding currentWedding;
    private Wedding draftWedding;

    /**
     * Initializes a WeddingModelManager with the given parameters.
     */
    public WeddingModelManager(ReadOnlyAddressBook addressBook,
                               ReadOnlyWeddingPlanner weddingPlanner,
                               ReadOnlyUserPrefs userPrefs) {
        super(addressBook, userPrefs); // Call base ModelManager constructor

        requireNonNull(weddingPlanner);
        logger.fine("Initializing with wedding planner: " + weddingPlanner);

        this.weddingPlanner = new WeddingPlanner(weddingPlanner);
        this.filteredWeddings = new FilteredList<>(this.weddingPlanner.getWeddingList());
        this.currentWedding = null;
        this.draftWedding = null;
    }

    /**
     * Creates an empty WeddingModelManager.
     */
    public WeddingModelManager() {
        this(new AddressBook(), new WeddingPlanner(), new UserPrefs());
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
    public void setCurrentWedding(Wedding wedding) {
        currentWedding = wedding;
        logger.info("Entered context for: " + wedding);
    }

    @Override
    public void clearCurrentWedding() {
        currentWedding = null;
        logger.info("Cleared wedding context");
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
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof WeddingModelManager)) {
            return false;
        }

        if (!super.equals(obj)) {
            return false;
        }

        WeddingModelManager other = (WeddingModelManager) obj;
        return weddingPlanner.equals(other.weddingPlanner)
                && filteredWeddings.equals(other.filteredWeddings)
                && java.util.Objects.equals(currentWedding, other.currentWedding)
                && java.util.Objects.equals(draftWedding, other.draftWedding);
    }
}
