package seedu.address.model.person.staff;

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
 * Represents a staff member in the wedding planner
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Staff extends Person {
    private final PaymentDetail paymentDetail;

    /**
     * Every field must be present and not null.
     */
    public Staff(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  PaymentDetail paymentDetail) {
        super(name, phone, email, address, tags);

        requireAllNonNull(paymentDetail);
        this.paymentDetail = paymentDetail;
    }

    public PaymentDetail getPaymentDetail() {
        return this.paymentDetail;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Staff otherStaff = (Staff) other;
        return super.equals(otherStaff) && paymentDetail.equals(otherStaff.paymentDetail);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(super.toString()).add("PaymentDetail", paymentDetail)
                .toString();
    }
}
