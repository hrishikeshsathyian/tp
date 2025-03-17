package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Represents the in-memory model of both address book and wedding planner data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    public static final Predicate<Wedding> PREDICATE_SHOW_ALL_WEDDINGS = unused -> true;


    private final AddressBook addressBook;
    private final WeddingPlanner weddingPlanner;
    private final UserPrefs userPrefs;
    
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Wedding> filteredWeddings;
    
    private Wedding currentWedding;
    private Wedding draftWedding;

    public ModelManager(ReadOnlyAddressBook addressBook, 
                       ReadOnlyWeddingPlanner weddingPlanner,
                       ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, weddingPlanner, userPrefs);

        logger.fine("Initializing with:\n- Address Book: " + addressBook 
                + "\n- Wedding Planner: " + weddingPlanner 
                + "\n- User Prefs: " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.weddingPlanner = new WeddingPlanner(weddingPlanner);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredWeddings = new FilteredList<>(this.weddingPlanner.getWeddingList());
    }

    /**
     * Creates empty ModelManager for new users.
     */
    public ModelManager() {
        this(new AddressBook(), new WeddingPlanner(), new UserPrefs());
    }

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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    public Path getWeddingPlannerFilePath() {
        return userPrefs.getWeddingPlannerFilePath();
    }


    public void setWeddingPlannerFilePath(Path weddingPlannerFilePath) {
        requireNonNull(weddingPlannerFilePath);
        userPrefs.setWeddingPlannerFilePath(weddingPlannerFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

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

    @Override
    public boolean weddingHasPerson(Wedding wedding, Person person) {
        requireAllNonNull(wedding, person);
        return wedding.hasPerson(person);
    }

    @Override
    public void addWeddingPerson(Wedding wedding, Person person) {
        requireAllNonNull(wedding, person);
        wedding.addMember(person);
        updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

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
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof ModelManager)) return false;

        ModelManager otherManager = (ModelManager) other;
        return addressBook.equals(otherManager.addressBook)
                && weddingPlanner.equals(otherManager.weddingPlanner)
                && userPrefs.equals(otherManager.userPrefs)
                && filteredPersons.equals(otherManager.filteredPersons)
                && filteredWeddings.equals(otherManager.filteredWeddings)
                && java.util.Objects.equals(currentWedding, otherManager.currentWedding)
                && java.util.Objects.equals(draftWedding, otherManager.draftWedding);
    }
}
