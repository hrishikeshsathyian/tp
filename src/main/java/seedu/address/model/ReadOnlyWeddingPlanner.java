package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.wedding.Wedding;

/**
 * Unmodifiable view of a wedding planner
 */
public interface ReadOnlyWeddingPlanner {

    /**
     * Returns an unmodifiable view of the weddings list.
     * This list will not contain any duplicate weddings.
     */
    ObservableList<Wedding> getWeddingList();

}
