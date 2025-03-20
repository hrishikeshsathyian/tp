package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;

public class WeddingModelManagerTest {

    private WeddingModelManager modelManager = new WeddingModelManager();

    private final Wedding WeddingA = new Wedding(
            new Date("25122025"),
            new Title("Alice & Bob")
    );

    private final Wedding WeddingB = new Wedding(
            new Date("01012026"),
            new Title("Charlie & Diana")
    );

    private final Person PersonA = new PersonBuilder().withName("Alice").build();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new WeddingPlanner(), new WeddingPlanner(modelManager.getWeddingPlanner()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setWeddingPlannerFilePath(Paths.get("wedding/planner/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setWeddingPlannerFilePath(Paths.get("new/wedding/planner/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setWeddingPlannerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setWeddingPlannerFilePath(null));
    }

    @Test
    public void setWeddingPlannerFilePath_validPath_setsWeddingPlannerFilePath() {
        Path path = Paths.get("wedding/planner/file/path");
        modelManager.setWeddingPlannerFilePath(path);
        assertEquals(path, modelManager.getWeddingPlannerFilePath());
    }

    @Test
    public void hasWedding_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasWedding(null));
    }

    @Test
    public void hasWedding_weddingNotInWeddingPlanner_returnsFalse() {
        assertFalse(modelManager.hasWedding(WeddingA));
    }

    @Test
    public void hasWedding_weddingInWeddingPlanner_returnsTrue() {
        modelManager.addWedding(WeddingA);
        assertTrue(modelManager.hasWedding(WeddingA));
    }

    @Test
    public void setDraftWedding_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setDraftWedding(null));
    }

    @Test
    public void setDraftWedding_validWedding_setsDraftWedding() {
        modelManager.setDraftWedding(WeddingA);
        assertEquals(WeddingA, modelManager.getDraftWedding());
        assertTrue(modelManager.hasDraftWedding());
    }

    @Test
    public void hasDraftWedding_noDraftWedding_returnsFalse() {
        assertFalse(modelManager.hasDraftWedding());
    }

    @Test
    public void getDraftWedding_noDraftWedding_returnsNull() {
        assertNull(modelManager.getDraftWedding());
    }

    @Test
    public void commitDraftWedding_noDraftWedding_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> modelManager.commitDraftWedding());
    }

    @Test
    public void commitDraftWedding_duplicateWedding_throwsIllegalStateException() {
        modelManager.addWedding(WeddingA);
        modelManager.setDraftWedding(WeddingA);
        assertThrows(IllegalStateException.class, () -> modelManager.commitDraftWedding());
    }

    @Test
    public void commitDraftWedding_validDraftWedding_addsWeddingAndClearsDraft() {
        modelManager.setDraftWedding(WeddingA);
        modelManager.commitDraftWedding();

        assertTrue(modelManager.hasWedding(WeddingA));
        assertFalse(modelManager.hasDraftWedding());
        assertNull(modelManager.getDraftWedding());
    }

    @Test
    public void setCurrentWedding_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCurrentWedding(null));
    }

    @Test
    public void setCurrentWedding_validWedding_setsCurrentWedding() {
        modelManager.setCurrentWedding(WeddingA);
        assertEquals(WeddingA, modelManager.getCurrentWedding());
        assertTrue(modelManager.hasCurrentWedding());
    }

    @Test
    public void clearCurrentWedding_removesCurrentWedding() {
        modelManager.setCurrentWedding(WeddingA);
        modelManager.clearCurrentWedding();

        assertFalse(modelManager.hasCurrentWedding());
        assertNull(modelManager.getCurrentWedding());
    }

    @Test
    public void weddingHasPerson_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.weddingHasPerson(null, PersonA));
    }

    @Test
    public void weddingHasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.weddingHasPerson(WeddingA, null));
    }

    @Test
    public void weddingHasPerson_personAsBride_returnsTrue() {
        Wedding wedding = new Wedding(new Date("25122025"), new Title("Test Wedding"));
        wedding.setBride(PersonA);

        assertTrue(modelManager.weddingHasPerson(wedding, PersonA));
    }

    @Test
    public void weddingHasPerson_personAsGroom_returnsTrue() {
        Wedding wedding = new Wedding(new Date("25122025"), new Title("Test Wedding"));
        wedding.setGroom(PersonA);

        assertTrue(modelManager.weddingHasPerson(wedding, PersonA));
    }

    @Test
    public void weddingHasPerson_personAsMember_returnsTrue() {
        Wedding wedding = new Wedding(new Date("25122025"), new Title("Test Wedding"));
        wedding.addMember(PersonA);

        assertTrue(modelManager.weddingHasPerson(wedding, PersonA));
    }

    @Test
    public void weddingHasPerson_personNotInWedding_returnsFalse() {
        Wedding wedding = new Wedding(new Date("25122025"), new Title("Test Wedding"));

        assertFalse(modelManager.weddingHasPerson(wedding, PersonA));
    }

    @Test
    public void addWeddingPerson_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addWeddingPerson(null, PersonA));
    }

    @Test
    public void addWeddingPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addWeddingPerson(WeddingA, null));
    }

    @Test
    public void addWeddingPerson_validWeddingAndPerson_addsPerson() {
        Wedding wedding = new Wedding(new Date("25122025"), new Title("Test Wedding"));
        modelManager.addWeddingPerson(wedding, PersonA);

        assertTrue(modelManager.weddingHasPerson(wedding, PersonA));
    }

    @Test
    public void getFilteredWeddingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredWeddingList().remove(0));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void updateFilteredWeddingList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredWeddingList(null));
    }

    @Test
    public void updateFilteredPersonList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredPersonList(null));
    }

    @Test
    public void equals() {
        WeddingPlanner weddingPlanner = new WeddingPlannerTest.WeddingPlannerBuilder()
                .withWedding(WeddingA)
                .withWedding(WeddingB)
                .build();
        WeddingPlanner differentWeddingPlanner = new WeddingPlanner();
        UserPrefs userPrefs = new UserPrefs();

        // Same values -> returns true
        WeddingModelManager modelManagerCopy = new WeddingModelManager(weddingPlanner, userPrefs);
        modelManager = new WeddingModelManager(weddingPlanner, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // Same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // Null -> returns false
        assertFalse(modelManager.equals(null));

        // Different types -> returns false
        assertFalse(modelManager.equals(5));

        // Different wedding planner -> returns false
        assertFalse(modelManager.equals(new WeddingModelManager(differentWeddingPlanner, userPrefs)));

        // Different current wedding -> returns false
        WeddingModelManager differentModelManager = new WeddingModelManager(weddingPlanner, userPrefs);
        modelManager.setCurrentWedding(WeddingA);
        assertFalse(modelManager.equals(differentModelManager));

        // Different draft wedding -> returns false
        differentModelManager = new WeddingModelManager(weddingPlanner, userPrefs);
        differentModelManager.setCurrentWedding(WeddingA);
        modelManager.clearCurrentWedding();
        modelManager.setDraftWedding(WeddingB);
        assertFalse(modelManager.equals(differentModelManager));

        // Different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setWeddingPlannerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new WeddingModelManager(weddingPlanner, differentUserPrefs)));
    }
}
