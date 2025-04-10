package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonWeddingPlannerStorage weddingPlannerStorage = new JsonWeddingPlannerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(weddingPlannerStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonAddressBookStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
     */
    /* TODO: EDIT TEST AND DATA TO MATCH WEDDINGPLANNER
    @Test
    public void addressBookReadSave() throws Exception {

        AddressBook original = getTypicalAddressBook();
        storageManager.saveWeddingPlanner(original);
        ReadOnlyWeddingPlanner retrieved = storageManager.readWeddingPlanner().get();
        assertEquals(original, new AddressBook(retrieved));
    }
    */

    @Test
    public void getWeddingPlannerFilePath() {
        assertNotNull(storageManager.getWeddingPlannerFilePath());
    }

}
