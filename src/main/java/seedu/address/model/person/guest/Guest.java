package seedu.address.model.person.guest;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a guest of a wedding
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Guest extends Person {
    private final Availability availability;

    /**
     * Every field must be present and not null. More fields may be added later.
     */
    public Guest(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Availability availability) {
        super(name, phone, email, address, tags);

        requireAllNonNull(availability);
        this.availability = availability;
    }

    public Availability getAvailability() {
        return this.availability;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Guest otherPerson = (Guest) other;
        return super.equals(other) && availability.equals(otherPerson.availability);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(super.toString()).add("availability", availability).toString();
    }
}
