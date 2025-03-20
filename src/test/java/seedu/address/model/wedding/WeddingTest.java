package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.PersonBuilder;

public class WeddingTest {

    private static final Date VALID_DATE = new Date("25122025");
    private static final Title VALID_TITLE = new Title("John & Mary");
    private static final Person VALID_BRIDE = new PersonBuilder().withName("Mary").withTags("bride").build();
    private static final Person VALID_GROOM = new PersonBuilder().withName("John").withTags("groom").build();
    private static final Person VALID_MEMBER = new PersonBuilder().withName("Jerry").withTags("bridesmaid").build();

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Wedding(null, VALID_TITLE));
        assertThrows(NullPointerException.class, () -> new Wedding(VALID_DATE, null));

        // Full constructor with null parameters
        assertThrows(NullPointerException.class, () -> new Wedding(null, VALID_TITLE, VALID_BRIDE, VALID_GROOM,
                new UniquePersonList()));
        assertThrows(NullPointerException.class, () -> new Wedding(VALID_DATE, null, VALID_BRIDE, VALID_GROOM,
                new UniquePersonList()));
        assertThrows(NullPointerException.class, () -> new Wedding(VALID_DATE, VALID_TITLE, null, VALID_GROOM,
                new UniquePersonList()));
        assertThrows(NullPointerException.class, () -> new Wedding(VALID_DATE, VALID_TITLE, VALID_BRIDE, null,
                new UniquePersonList()));
        assertThrows(NullPointerException.class, () -> new Wedding(VALID_DATE, VALID_TITLE, VALID_BRIDE, VALID_GROOM,
                null));
    }

    @Test
    public void setters_nullValues_throwsNullPointerException() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);

        assertThrows(NullPointerException.class, () -> wedding.setBride(null));
        assertThrows(NullPointerException.class, () -> wedding.setGroom(null));
        assertThrows(NullPointerException.class, () -> wedding.setMembers(null));
        assertThrows(NullPointerException.class, () -> wedding.addMember(null));
    }

    @Test
    public void getters_validValues_returnsCorrectValues() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);

        assertEquals(VALID_DATE, wedding.getDate());
        assertEquals(VALID_TITLE, wedding.getTitle());
    }

    @Test
    public void setters_validValues_setsCorrectValues() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);

        wedding.setBride(VALID_BRIDE);
        assertEquals(VALID_BRIDE, wedding.getBride());

        wedding.setGroom(VALID_GROOM);
        assertEquals(VALID_GROOM, wedding.getGroom());

        wedding.addMember(VALID_MEMBER);
        assertTrue(wedding.getMembers().contains(VALID_MEMBER));

        UniquePersonList newMembers = new UniquePersonList();
        Person newMember = new PersonBuilder().withName("Alex").build();
        newMembers.add(newMember);

        wedding.setMembers(newMembers);
        assertEquals(newMembers, wedding.getMembers());
    }

    @Test
    public void hasPerson_personInWedding_returnsTrue() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);

        wedding.setBride(VALID_BRIDE);
        assertTrue(wedding.hasPerson(VALID_BRIDE));

        wedding.setGroom(VALID_GROOM);
        assertTrue(wedding.hasPerson(VALID_GROOM));

        wedding.addMember(VALID_MEMBER);
        assertTrue(wedding.hasPerson(VALID_MEMBER));
    }

    @Test
    public void hasPerson_personNotInWedding_returnsFalse() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);
        Person person = new PersonBuilder().withName("Stranger").build();

        assertFalse(wedding.hasPerson(person));
    }

    @Test
    public void isValid_hasBrideAndGroom_returnsTrue() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);
        wedding.setBride(VALID_BRIDE);
        wedding.setGroom(VALID_GROOM);

        assertTrue(wedding.isValid());
    }

    @Test
    public void isValid_missingBrideOrGroom_returnsFalse() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);

        // No bride or groom
        assertFalse(wedding.isValid());

        // Only bride
        wedding.setBride(VALID_BRIDE);
        assertFalse(wedding.isValid());

        // Only groom
        wedding = new Wedding(VALID_DATE, VALID_TITLE);
        wedding.setGroom(VALID_GROOM);
        assertFalse(wedding.isValid());
    }

    @Test
    public void equals() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);

        // Same instance -> returns true
        assertTrue(wedding.equals(wedding));

        // Same values -> returns true
        Wedding weddingCopy = new Wedding(VALID_DATE, VALID_TITLE);
        assertTrue(wedding.equals(weddingCopy));

        // Different type -> returns false
        assertFalse(wedding.equals(5));

        // Null -> returns false
        assertFalse(wedding.equals(null));

        // Different date -> returns false
        Wedding differentDate = new Wedding(new Date("11112025"), VALID_TITLE);
        assertFalse(wedding.equals(differentDate));

        // Different title -> returns false
        Wedding differentTitle = new Wedding(VALID_DATE, new Title("Different Title"));
        assertFalse(wedding.equals(differentTitle));
    }

    @Test
    public void isSameWedding() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);

        // Same instance -> returns true
        assertTrue(wedding.isSameWedding(wedding));

        // Same values -> returns true
        Wedding weddingCopy = new Wedding(VALID_DATE, VALID_TITLE);
        assertTrue(wedding.isSameWedding(weddingCopy));

        // Different date -> returns false
        Wedding differentDate = new Wedding(new Date("11112025"), VALID_TITLE);
        assertFalse(wedding.isSameWedding(differentDate));

        // Different title -> returns false
        Wedding differentTitle = new Wedding(VALID_DATE, new Title("Different Title"));
        assertFalse(wedding.isSameWedding(differentTitle));
    }

    @Test
    public void toString_validWedding_returnsCorrectString() {
        Wedding wedding = new Wedding(VALID_DATE, VALID_TITLE);
        String expected = String.format("%s (%s)", VALID_TITLE, VALID_DATE);
        assertEquals(expected, wedding.toString());
    }
}
