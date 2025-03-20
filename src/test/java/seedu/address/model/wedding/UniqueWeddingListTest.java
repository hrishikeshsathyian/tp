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

    private final Wedding weddingA = new Wedding(
            new Date("25122025"),
            new Title("Alice & Bob")
    );

    private final Wedding weddingB = new Wedding(
            new Date("01012026"),
            new Title("Charlie & Diana")
    );

    @Test
    public void contains_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.contains(null));
    }

    @Test
    public void contains_weddingNotInList_returnsFalse() {
        assertFalse(uniqueWeddingList.contains(weddingA));
    }

    @Test
    public void contains_weddingInList_returnsTrue() {
        uniqueWeddingList.add(weddingA);
        assertTrue(uniqueWeddingList.contains(weddingA));
    }

    @Test
    public void contains_weddingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWeddingList.add(weddingA);
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
        uniqueWeddingList.add(weddingA);
        assertThrows(DuplicateWeddingException.class, () -> uniqueWeddingList.add(weddingA));
    }

    @Test
    public void remove_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.remove(null));
    }

    @Test
    public void remove_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> uniqueWeddingList.remove(weddingA));
    }

    @Test
    public void remove_existingWedding_removesWedding() {
        uniqueWeddingList.add(weddingA);
        uniqueWeddingList.remove(weddingA);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullUniqueWeddingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_uniqueWeddingList_replacesOwnListWithProvidedUniqueWeddingList() {
        uniqueWeddingList.add(weddingA);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(weddingB);
        uniqueWeddingList.setWeddings(expectedUniqueWeddingList.asUnmodifiableObservableList());
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWeddingList.setWeddings((List<Wedding>) null));
    }

    @Test
    public void setWeddings_list_replacesOwnListWithProvidedList() {
        uniqueWeddingList.add(weddingA);
        List<Wedding> weddingList = Collections.singletonList(weddingB);
        uniqueWeddingList.setWeddings(weddingList);
        UniqueWeddingList expectedUniqueWeddingList = new UniqueWeddingList();
        expectedUniqueWeddingList.add(weddingB);
        assertEquals(expectedUniqueWeddingList, uniqueWeddingList);
    }

    @Test
    public void setWeddings_listWithDuplicateWeddings_throwsDuplicateWeddingException() {
        List<Wedding> listWithDuplicateWeddings = Arrays.asList(weddingA, weddingA);
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
        uniqueWeddingList.add(weddingA);
        anotherUniqueWeddingList.add(weddingA);
        assertEquals(uniqueWeddingList.hashCode(), anotherUniqueWeddingList.hashCode());
    }

    @Test
    public void toStringTest() {
        // Empty list
        assertEquals(uniqueWeddingList.asUnmodifiableObservableList().toString(), uniqueWeddingList.toString());

        // Non-empty list
        uniqueWeddingList.add(weddingA);
        assertEquals(uniqueWeddingList.asUnmodifiableObservableList().toString(), uniqueWeddingList.toString());
    }
}
