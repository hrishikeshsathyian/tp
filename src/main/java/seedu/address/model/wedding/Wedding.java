package seedu.address.model.wedding;
import seedu.address.model.person.Person;
/**
 * Represents a Wedding in the address book.
 * Guarantees: details are present, validated and not null.
 */
public class Wedding {

    private final Date date;

    private final Title title;

    private Person bride;

    private Person groom;

    private Person[] members;


    /**
     * Every field must be present and not null.
     */
    public Wedding(Date date, Title name) {
        this.date = date;
        this.title = name;
    }

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
        boolean isEqual = this.date.equals(otherWedding.date) && this.title.equals(otherWedding.title);
        return isEqual;
    }


    public boolean isSameWedding(Wedding wedding) {
        return this.equals(wedding);
    }

}
