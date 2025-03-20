package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;

public class WeddingPlannerTest {

    private final WeddingPlanner weddingPlanner = new WeddingPlanner();
    private final Wedding WEDDING_A = new Wedding(
            new Date("25122025"),
            new Title("Alice & Bob")
    );
    private final Wedding WEDDING_B = new Wedding(
            new Date("01012026"),
            new Title("Charlie & Diana")
    );

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), weddingPlanner.getWeddingList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddingPlanner.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyWeddingPlanner_replacesData() {
        WeddingPlanner newData = new WeddingPlannerBuilder().withWedding(WEDDING_B).build();
        weddingPlanner.resetData(newData);
        assertEquals(newData, weddingPlanner);
    }

    @Test
    public void resetData_withDuplicateWeddings_throwsDuplicateWeddingException() {
        // Two weddings with the same identity fields
        Wedding editedWeddingA = new Wedding(
                new Date("25122025"),
                new Title("Alice & Bob")
        );

        ObservableList<Wedding> newWeddings = FXCollections.observableArrayList(WEDDING_A, editedWeddingA);
        WeddingPlannerStub newData = new WeddingPlannerStub(newWeddings);

        assertThrows(DuplicateWeddingException.class, () -> weddingPlanner.resetData(newData));
    }

    @Test
    public void hasWedding_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddingPlanner.hasWedding(null));
    }

    @Test
    public void hasWedding_weddingNotInWeddingPlanner_returnsFalse() {
        assertFalse(weddingPlanner.hasWedding(WEDDING_A));
    }

    @Test
    public void hasWedding_weddingInWeddingPlanner_returnsTrue() {
        weddingPlanner.addWedding(WEDDING_A);
        assertTrue(weddingPlanner.hasWedding(WEDDING_A));
    }

    @Test
    public void hasWedding_weddingWithSameIdentityFieldsInWeddingPlanner_returnsTrue() {
        weddingPlanner.addWedding(WEDDING_A);
        Wedding editedWeddingA = new Wedding(
                new Date("25122025"),
                new Title("Alice & Bob")
        );
        assertTrue(weddingPlanner.hasWedding(editedWeddingA));
    }

    @Test
    public void getWeddingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> weddingPlanner.getWeddingList().remove(0));
    }

    @Test
    public void toString_validWeddingPlanner_returnsString() {
        String actualOutput = weddingPlanner.toString();
        System.out.println("Actual output: " + actualOutput);

        assertTrue(actualOutput.contains("WeddingPlanner"));
        assertTrue(actualOutput.contains("weddings"));
    }

    @Test
    public void equals() {
        // Same object -> returns true
        assertTrue(weddingPlanner.equals(weddingPlanner));

        // Different type -> returns false
        assertFalse(weddingPlanner.equals(5));

        // Null -> returns false
        assertFalse(weddingPlanner.equals(null));

        // Different weddings -> returns false
        WeddingPlanner anotherPlanner = new WeddingPlannerBuilder().withWedding(WEDDING_A).build();
        assertFalse(weddingPlanner.equals(anotherPlanner));

        // Same weddings -> returns true
        weddingPlanner.addWedding(WEDDING_A);
        assertTrue(weddingPlanner.equals(anotherPlanner));
    }

    /**
     * A stub ReadOnlyWeddingPlanner whose weddings list can violate interface constraints.
     */
    private static class WeddingPlannerStub implements ReadOnlyWeddingPlanner {
        private final ObservableList<Wedding> weddings;

        WeddingPlannerStub(Collection<Wedding> weddings) {
            this.weddings = FXCollections.observableArrayList(weddings);
        }

        @Override
        public ObservableList<Wedding> getWeddingList() {
            return weddings;
        }
    }

    /**
     * A utility class to help with building WeddingPlanner objects.
     */
    public static class WeddingPlannerBuilder {
        private WeddingPlanner weddingPlanner;

        public WeddingPlannerBuilder() {
            weddingPlanner = new WeddingPlanner();
        }

        /**
         * Adds a wedding to the wedding planner being built.
         */
        public WeddingPlannerBuilder withWedding(Wedding wedding) {
            weddingPlanner.addWedding(wedding);
            return this;
        }

        /**
         * Adds multiple weddings to the wedding planner being built.
         */
        public WeddingPlannerBuilder withWeddings(Wedding... weddings) {
            for (Wedding wedding : weddings) {
                weddingPlanner.addWedding(wedding);
            }
            return this;
        }

        public WeddingPlanner build() {
            return weddingPlanner;
        }
    }
}
