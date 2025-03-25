package seedu.address.model.person.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.Address;

/**
 * Represents a staff's payment status in the wedding planner.
 * This is up to the user to define: It can be free, paid, pending, etc
 * Guarantees: Immutable; is valid as declared in {@link #isValidPaymentDetail(String)}
 */
public class PaymentDetail {
    public static final String MESSAGE_CONSTRAINTS = "Payment Details can take any values, and "
        + "it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code PaymentDetail}.
     *
     * @param detail A valid payment detail.
     */
    public PaymentDetail(String detail) {
        requireNonNull(detail);
        checkArgument(isValidPaymentDetail(detail), MESSAGE_CONSTRAINTS);
        value = detail;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidPaymentDetail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        PaymentDetail otherPaymentDetail = (PaymentDetail) other;
        return value.equals(otherPaymentDetail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
