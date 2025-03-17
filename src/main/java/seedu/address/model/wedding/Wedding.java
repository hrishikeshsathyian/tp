package seedu.address.model.wedding;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Wedding in the address book.
 * Guarantees: details are present, validated and not null.
 */
public class Wedding {  // check if this class is correct later

    private final Date date;

    private final Title title;

    private Person bride;

    private Person groom;

    private UniquePersonList members;


    /**
     * Every field must be present and not null.
     */
    public Wedding(Date date, Title name) {
        requireAllNonNull(date, name);
        this.date = date;
        this.title = name;
    }

    /**
     * Overloaded Wedding constructor to be used for file storage loading
     * @param date
     * @param title
     * @param bride
     * @param groom
     * @param members
     */
    public Wedding(Date date, Title title, Person bride, Person groom, UniquePersonList members) {
        requireAllNonNull(date, title, bride, groom, members);
        this.date = date;
        this.title = title;
        this.bride = bride;
        this.groom = groom;
        this.members = members;
    }

    public Title getTitle() {
        return this.title;
    }

    public Date getDate() {
        return this.date;
    }

    public void setBride(Person bride) {
        this.bride = bride;
    }
    
    public void setGroom(Person groom) {
        this.groom = groom;
    }
    
    public void addMember(Person member) {
        members.add(member);
    }

    /**
     * Returns true if both Weddings have the same title and date.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Wedding)) {
            return false;
        }

        Wedding otherWedding = (Wedding) other;
        boolean isEqual = this.date.equals(otherWedding.date) && this.title.equals(otherWedding.title);
        return isEqual;
    }


    public boolean isSameWedding(Wedding wedding) {
        return this.equals(wedding);
    }

}
