package seedu.address.model.wedding;
import seedu.address.model.person.Person;
/**
 * Represents a Wedding in the address book.
 * Guarantees: details are present, validated and not null.
 */
public class Wedding {

    private final Date date;

    private final Title title;

    private final Person bride;

    private final Person groom;

    private Person[] members;


    public Wedding(Date date, Title name, Person bride, Person groom ) {
        this.date = date;
        this.title = name;
        this.bride = bride;
        this.groom = groom;
    }
}
