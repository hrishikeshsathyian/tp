package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;

public class UniqueWeddingListTest {

    private final UniqueWeddingList uniqueWeddingList = new UniqueWeddingList();

    private final Wedding WEDDING_A = new Wedding(
            new Date("25122025"),
            new Title("Alice & Bob")
    );

    private final Wedding WEDDING_B = new Wedding(
            new Date("01012026"),
            new Title("Charlie & Diana")
    );

    @Test
    public void contains_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.contains(null));
    }

    @Test
    public void contains_weddingNotInList_returnsFalse() {
        assertFalse(uniqueWeddingList.contains(WEDDING_A));
    }

    @Test
    public void contains_weddingInList_returnsTrue() {
        uniqueWeddingList.add(WEDDING_A);
        assertTrue(uniqueWeddingList.contains(WEDDING_A));
    }

    @Test
    public void contains_weddingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWeddingList.add(WEDDING_A);
        Wedding weddingSameIdentity = new Wedding(
                new Date("25122025"),
                new Title("Alice & Bob")
        );
        assertTrue(uniqueWeddingList.contains(weddingSameIdentity));
    }

    @Test
    public void add_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.add(null));
    }

    @Test
    public void add_duplicateWedding_throwsDuplicateWeddingException() {
        uniqueWeddingList.add(WEDDING_A);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.add(WEDDING_A));
    }

    @Test
    public void remove_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.remove(null));
    }

    @Test
    public void remove_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.remove(WEDDING_A));
    }

    @Test
    public void remove_existingWedding_removesWedding() {
        uniqueWeddingList.add(WEDDING_A);
        uniqueWeddingList.remove(WEDDING_A);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullUniqueWeddingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_uniqueWeddingList_replacesOwnListWithProvidedUniqueWeddingList() {
        uniqueWeddingList.add(WEDDING_A);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(WEDDING_B);
        uniqueWeddingList.setWeddings(expectedUniqueWeddingList.asUnmodifiableObservableList());
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_list_replacesOwnListWithProvidedList() {
        uniqueWeddingList.add(WEDDING_A);
        List<Wedding> weddingList = Collections.singletonList(WEDDING_B);
        uniqueWeddingList.setWeddings(weddingList);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(WEDDING_B);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_listWithDuplicateWeddings_throwsDuplicateWeddingException() {
        List<Wedding> listWithDuplicateWeddings = Arrays.asList(WEDDING_A, WEDDING_A);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.setWeddings(listWithDuplicateWeddings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueWeddingList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void hashCodeTest() {
        UniqueWeddingList anotherUniqueWeddingList = new UniqueWeddingList();

        // Empty lists should have same hashcode
        assertEquals(uniqueWeddingList.hashCode(), anotherUniqueWeddingList.hashCode());

        // After adding the same wedding, they should still have the same hashcode
        uniqueWeddingList.add(WEDDING_A);
        anotherUniqueWeddingList.add(WEDDING_A);
        assertEquals(uniqueWeddingList.hashCode(), anotherUniqueWeddingList.hashCode());
    }

    @Test
    public void toStringTest() {
        // Empty list
        assertEquals(uniqueWeddingList.asUnmodifiableObservableList().toString(), uniqueWeddingList.toString());

        // Non-empty list
        uniqueWeddingList.add(WEDDING_A);
        assertEquals(uniqueWeddingList.asUnmodifiableObservableList().toString(), uniqueWeddingList.toString());
    }
}
