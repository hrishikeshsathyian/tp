package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.wedding.Wedding;

/**
 * Represents the in-memory model of the Wedding Planner data.
 */
public class WeddingModelManager implements WeddingModel {

    private final WeddingPlanner weddingPlanner;
    private Wedding currentWedding;
    private Wedding draftWedding;

    /**
     * Initializes a WeddingModelManager with the given Wedding Planner
     */
    public WeddingModelManager(WeddingPlanner weddingPlanner) {
        this.weddingPlanner = weddingPlanner;
        this.currentWedding = null;
        this.draftWedding = null;
    }

    // =========== Permanent Storage Operations ===========
    
    @Override
    public void addWedding(Wedding wedding) {
        weddingPlanner.addWedding(wedding);
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        return weddingPlanner.hasWedding(wedding);
    }

    // =========== Draft Management ===========
    
    @Override
    public void setDraftWedding(Wedding wedding) {
        draftWedding = wedding;
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
            throw new IllegalStateException("No draft wedding to commit");
        }
        addWedding(draftWedding);
        draftWedding = null;
    }

    // =========== Context Management ===========
    
    @Override
    public void setCurrentWedding(Wedding wedding) {
        currentWedding = wedding;
    }

    @Override
    public void clearCurrentWedding() {
        currentWedding = null;
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
    public boolean weddingHasPerson(Wedding wedding, Person toAdd) {
        requireAllNonNull(wedding, toAdd);
        
        // Check bride
        if (wedding.getBride() != null && wedding.getBride().isSamePerson(toAdd)) {
            return true;
        }
        
        // Check groom
        if (wedding.getGroom() != null && wedding.getGroom().isSamePerson(toAdd)) {
            return true;
        }
        
        // Check members
        if (wedding.getMembers() != null) {
            return wedding.getMembers().contains(toAdd);
        }
        
        return false;
    }

    @Override
    public void addWeddingPerson(Wedding wedding, Person toAdd) {
        requireAllNonNull(wedding, toAdd);
        
        // Initialize members list if null
        if (wedding.getMembers() == null) {
            wedding.setMembers(new UniquePersonList());
        }
        
        wedding.getMembers().add(toAdd);
    }

    // =========== Utility Methods ===========
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof WeddingModelManager)) {
            return false;
        }

        WeddingModelManager other = (WeddingModelManager) obj;
        return weddingPlanner.equals(other.weddingPlanner)
                && ((currentWedding == null && other.currentWedding == null)
                    || (currentWedding != null && currentWedding.equals(other.currentWedding)))
                && ((draftWedding == null && other.draftWedding == null)
                    || (draftWedding != null && draftWedding.equals(other.draftWedding)));
    }
}
