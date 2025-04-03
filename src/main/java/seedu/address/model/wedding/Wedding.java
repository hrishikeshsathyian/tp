package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
     * Every field must be present and not null.
     */
    public Wedding(Date date, Title title) {
        requireAllNonNull(date, title);
        this.date = date;
        this.title = title;
        this.members = new UniquePersonList(); // Initialize empty list
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

    // =========== Getters ===========
    public Person getBride() {
        return bride;
    }

    public Person getGroom() {
        return groom;
    }

    public UniquePersonList getMembers() {

        System.out.println(members);
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

    /**
     * Adds a person to the list of members in this wedding.
     */
    public void addMember(Person member) {
        requireNonNull(member);
        members.add(member);
    }

    public void setMembers(UniquePersonList members) {
        requireNonNull(members);
        this.members = members;
    }

    /**
     * Checks if the wedding contains the specified person.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);

        // Check if person is the bride or groom
        boolean isBride = bride != null && bride.isSamePerson(person);
        boolean isGroom = groom != null && groom.isSamePerson(person);

        // Check if person is in members list
        boolean isMember = members != null && members.contains(person);

        return isBride || isGroom || isMember;
    }

    /**
     * To see if a wedding contains a bride and a groom, or if it should remain as a draft.
     * @return
     */
    public boolean isValid() {
        return this.getGroom() != null && this.getBride() != null;
    }

    // =========== Core Methods ===========
    public Title getTitle() {
        return this.title;
    }

    public Date getDate() {
        return this.date;
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
        return date.equals(otherWedding.date)
            && title.equals(otherWedding.title);
    }


    public boolean isSameWedding(Wedding wedding) {
        return this.equals(wedding);
    }

    @Override
    public String toString() {
        return this.getTitle().toString();
    }

}
