package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;

/**
 * Represents a Wedding Planner that manages a collection of weddings.
 * Acts as a central storage for wedding-related data.
 */
public class WeddingPlanner implements ReadOnlyWeddingPlanner {
    private final UniqueWeddingList weddings;

    /**
     * Constructs an empty {@code WeddingPlanner}.
     */
    public WeddingPlanner() {
        this.weddings = new UniqueWeddingList();
    }

    /**
     * Creates a WeddingPlanner using weddings from the {@code toBeCopied}
     */
    public WeddingPlanner(ReadOnlyWeddingPlanner toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data with {@code newData}
     */
    public void resetData(ReadOnlyWeddingPlanner newData) {
        setWeddings(newData.getWeddingList());
    }

    //=========== Wedding Operations ======================================================================

    /**
     * Adds a wedding to the planner.
     * @throws DuplicateWeddingException if duplicate wedding exists
     */
    public void addWedding(Wedding toAdd) {
        weddings.add(toAdd);
    }

    /**
     * Replaces current weddings with {@code weddings}
     */
    public void setWeddings(ObservableList<Wedding> weddings) {
        this.weddings.setWeddings(weddings);
    }

    //=========== ReadOnly Accessors ======================================================================

    @Override
    public ObservableList<Wedding> getWeddingList() {
        return weddings.asUnmodifiableObservableList();
    }

    public boolean hasWedding(Wedding wedding) {
        return weddings.contains(wedding);
    }

    public void sortWeddings() {
        this.weddings.sort();
    }


    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WeddingPlanner
                && weddings.equals(((WeddingPlanner) other).weddings));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("weddings", weddings)
                .toString();
    }

    public void deleteWedding(Wedding toRemove) {
        this.weddings.deleteWedding(toRemove);
    }
}
