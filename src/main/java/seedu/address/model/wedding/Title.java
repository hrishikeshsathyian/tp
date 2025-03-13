package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wedding's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS =
            "Wedding Names should only be maximum 100 characters and should not be blank.";
    public static final int WEDDING_NAME_MAX_LENGTH = 100;

    public final String weddingTitle;

    /**
     * Constructs a {@code Name}.
     *
     * @param weddingTitle A valid wedding name.
     * @throws NullPointerException if {@code weddingName} is null.
     * @throws IllegalArgumentException if {@code weddingName} is blank or exceeds 100 characters.
     */
    public Title(String weddingTitle) {
        requireNonNull(weddingTitle);
        checkArgument(isValidName(weddingTitle), MESSAGE_CONSTRAINTS);
        this.weddingTitle = weddingTitle;
    }

    /**
     * Returns true if a given string is a valid wedding name.
     * A valid wedding name is non-blank and does not exceed {@code WEDDING_NAME_MAX_LENGTH} characters.
     *
     * @param test the wedding name string to validate.
     * @return true if the string is valid, false otherwise.
     */
    public static boolean isValidName(String test) {
        boolean isValid = test != null && !test.trim().isEmpty() && test.length() <= WEDDING_NAME_MAX_LENGTH;
        return isValid;
    }

    /**
     * Returns the wedding name in string format.
     *
     * @return the wedding name.
     */
    @Override
    public String toString() {
        return weddingTitle;
    }

    /**
     * Checks if this {@code Name} is equal to another object.
     * Two {@code Name} objects are considered equal if they have the same wedding name.
     *
     * @param other the object to compare with.
     * @return true if the other object is a {@code Name} with the same wedding name, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Title)) {
            return false;
        }
        Title otherName = (Title) other;
        return weddingTitle.equals(otherName.weddingTitle);
    }


    @Override
    public int hashCode() {
        return weddingTitle.hashCode();
    }
}
