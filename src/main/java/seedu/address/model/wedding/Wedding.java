package seedu.address.model.wedding;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Wedding in the address book.
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

    public Person getBride() {
        return this.bride;
    }

    public Person getGroom() {
        return this.groom;
    }

    public UniquePersonList getMembers() {
        return this.members;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", title)
                .add("date", date)
                .add("bride", bride)
                .add("groom", groom)
                .add("members", members)
                .toString();
    }

}
