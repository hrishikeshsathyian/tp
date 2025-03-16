package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Represents the in-memory model of the Wedding Planner data.
 */
public class WeddingModelManager implements WeddingModel {

    private final WeddingPlanner weddingPlanner;

    /**
     * Initializes a WeddingModelManager with the given Wedding Planner
     * @param weddingPlanner
     */
    public WeddingModelManager(WeddingPlanner weddingPlanner) {
        this.weddingPlanner = weddingPlanner;
    }

    /**
     * Adds the given wedding.
     * {@code Wedding} must not already exist in the Wedding Planner.
     *
     * @param wedding
     */
    @Override
    public void addWedding(Wedding wedding) {
        this.weddingPlanner.addWedding(wedding);
    }

    /**
     * Returns true if a wedding with the same identity as {@code Wedding} exists in the Wedding Planner.
     *
     * @param wedding
     */
    @Override
    public boolean hasWedding(Wedding wedding) {
        return this.weddingPlanner.hasWedding(wedding);
    }

    /**
     * Checks for duplicate Person in {@code wedding}.
     *
     * @param wedding
     * @param toAdd
     * @return true if the Wedding already has a Person with the same identity as {@code Person}
     */
    @Override
    public boolean weddingHasPerson(Wedding wedding, Person toAdd) {
        return false;
    }

    /**
     * Adds a Person to the given Wedding.
     *
     * @param wedding
     * @param toAdd
     */
    @Override
    public void addWeddingPerson(Wedding wedding, Person toAdd) {

    }

    @Override
    public ObservableList<Wedding> getWeddingList() {
        return weddingPlanner.getWeddingList();
    }
}
