package seedu.address.model.wedding;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Wedding in the wedding planner.
 * Guarantees: details are present, validated and not null.
 */
public class Wedding {

    private final Date date;
    private final Title title;
    private Person bride;
    private Person groom;
    private UniquePersonList members;

    /**
     * Creates a wedding draft with empty participant lists
     */
    public Wedding(Date date, Title title) {
        requireAllNonNull(date, title);
        this.date = date;
        this.title = title;
        this.members = new UniquePersonList(); // Initialize empty list
    }

    /**
     * Full constructor for loading from storage
     */
    public Wedding(Date date, Title title, Person bride, Person groom, UniquePersonList members) {
        requireAllNonNull(date, title, bride, groom, members);
        this.date = date;
        this.title = title;
        this.bride = bride;
        this.groom = groom;
        this.members = members;
    }

    // =========== Getters ===========
    public Person getBride() {
        return bride;
    }

    public Person getGroom() {
        return groom;
    }

    public UniquePersonList getMembers() {
        return members;
    }

    // =========== Setters ===========
    public void setBride(Person bride) {
        requireNonNull(bride);
        this.bride = bride;
    }
    
    public void setGroom(Person groom) {
        requireNonNull(groom);
        this.groom = groom;
    }

    public void addMember(Person member) {
        requireNonNull(member);
        members.add(member);
    }

    public void setMembers(UniquePersonList members) {
        requireNonNull(members);
        this.members = members;
    }

    // =========== Core Methods ===========
    public Title getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns true if both weddings have the same title and date.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Wedding)) return false;
        
        Wedding otherWedding = (Wedding) other;
        return date.equals(otherWedding.date) 
            && title.equals(otherWedding.title);
    }

    public boolean isSameWedding(Wedding other) {
        return equals(other);
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", title, date);
    }
}
