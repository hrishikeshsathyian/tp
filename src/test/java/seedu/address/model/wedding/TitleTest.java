package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));

        // Blank string
        assertThrows(IllegalArgumentException.class, () -> new Title("   "));

        // Too long (over 100 characters)
        String tooLongTitle = "This title is way too long and exceeds the maximum allowed length of one hundred characters which is definitely not allowed in our system";
        assertThrows(IllegalArgumentException.class, () -> new Title(tooLongTitle));
    }

    @Test
    public void isValidTitle() {
        // null title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid titles
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle("   ")); // spaces only

        // Title exceeding maximum length (100 characters)
        String tooLongTitle = "This title is way too long and exceeds the maximum allowed length of one hundred characters which is definitely not allowed in our system";
        assertFalse(Title.isValidTitle(tooLongTitle));

        // valid titles
        assertTrue(Title.isValidTitle("John & Mary")); // normal title
        assertTrue(Title.isValidTitle("A")); // single character
        assertTrue(Title.isValidTitle("Super Long Wedding Title But Still Under The Limit Of One Hundred Characters So It Should Be Valid.")); // long but valid
        assertTrue(Title.isValidTitle("Special Characters: !@#$%^&*()_+-=[]{}|;':\",./<>?")); // special characters
        assertTrue(Title.isValidTitle("12345")); // numbers only
    }

    @Test
    public void equals() {
        Title title = new Title("John & Mary");

        // same values -> returns true
        assertTrue(title.equals(new Title("John & Mary")));

        // same object -> returns true
        assertTrue(title.equals(title));

        // null -> returns false
        assertFalse(title.equals(null));

        // different types -> returns false
        assertFalse(title.equals(5.0f));

        // different values -> returns false
        assertFalse(title.equals(new Title("Different Title")));
    }

    @Test
    public void toString_returnsWeddingTitle() {
        String titleString = "John & Mary";
        Title title = new Title(titleString);
        assertTrue(title.toString().equals(titleString));
    }
}
