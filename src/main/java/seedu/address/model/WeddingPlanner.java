package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.wedding.Wedding;


/**
 * Represents a Wedding Planner that manages a collection of weddings.
 * Acts as a central storage for wedding-related data.
 */
public class WeddingPlanner {

    private final List<Wedding> weddings;

    /**
     * Constructs an empty {@code WeddingPlanner}.
     * Initializes an empty list to store weddings.
     */
    public WeddingPlanner() {
        this.weddings = new ArrayList<>();
    }

    /**
     * Adds a wedding to the wedding planner.
     *
     * @param toAdd The wedding to be added.
     */
    public void addWedding(Wedding toAdd) {
        this.weddings.add(toAdd);
    }

    /**
     * Checks if a given wedding exists in the wedding planner.
     *
     * @param otherWedding The wedding to check for existence.
     * @return {@code true} if the wedding exists, {@code false} otherwise.
     */
    public boolean hasWedding(Wedding otherWedding) {
        return this.weddings.contains(otherWedding);
    }
}
