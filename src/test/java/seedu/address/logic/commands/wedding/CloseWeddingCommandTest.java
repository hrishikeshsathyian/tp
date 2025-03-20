package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.WeddingModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;

public class CloseWeddingCommandTest {

    private WeddingModel model;
    private Wedding testWedding;
    private CloseWeddingCommand command;

    @BeforeEach
    public void setUp() {
        model = new WeddingModelManager();
        testWedding = new Wedding(new Date("25122025"), new Title("John & Mary"));
        command = new CloseWeddingCommand();
    }

    @Test
    public void execute_noOpenWedding_throwsCommandException() {
        assertThrows(
                CommandException.class,
                CloseWeddingCommand.MESSAGE_NO_OPEN_WEDDING,
                () -> command.execute(model)
        );
    }

    @Test
    public void execute_withOpenWedding_success() throws Exception {
        // Set up a wedding and set it as current
        model.addWedding(testWedding);
        model.setCurrentWedding(testWedding);

        // Verify wedding is open
        assertTrue(model.hasCurrentWedding());
        assertEquals(testWedding, model.getCurrentWedding());

        CommandResult result = command.execute(model);

        // Verify the result
        assertEquals(CloseWeddingCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertFalse(model.hasCurrentWedding());
        assertNull(model.getCurrentWedding());
    }

    @Test
    public void execute_closeWeddingWithModifications_preservesChanges() throws Exception {
        // Set up a wedding with bride and groom
        Person bride = new PersonBuilder().withName("Mary").withTags("bride").build();
        Person groom = new PersonBuilder().withName("John").withTags("groom").build();

        testWedding.setBride(bride);
        testWedding.setGroom(groom);

        // Add to storage and set as current
        model.addWedding(testWedding);
        model.setCurrentWedding(testWedding);

        // Add a guest to the current wedding
        Person guest = new PersonBuilder().withName("Guest").build();
        testWedding.addMember(guest);

        // Execute the close command and verify closing
        CommandResult result = command.execute(model);
        assertEquals(CloseWeddingCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertFalse(model.hasCurrentWedding());

        // Reopen the wedding to verify changes were saved
        model.setCurrentWedding(model.getFilteredWeddingList().get(0));
        Wedding reopenedWedding = model.getCurrentWedding();

        // Verify the changes (added guest) persisted
        assertEquals(bride, reopenedWedding.getBride());
        assertEquals(groom, reopenedWedding.getGroom());
        assertTrue(reopenedWedding.hasPerson(guest));
    }

    @Test
    public void execute_closeAndReopenWedding_canModifyAgain() throws Exception {
        // Set up a wedding
        model.addWedding(testWedding);
        model.setCurrentWedding(testWedding);

        // Close the wedding and verify closing
        command.execute(model);
        assertFalse(model.hasCurrentWedding());

        // Reopen the wedding
        model.setCurrentWedding(model.getFilteredWeddingList().get(0));

        // Add a person to verify we can modify after reopening
        Person person = new PersonBuilder().build();
        AddWeddingPersonCommand addCommand = new AddWeddingPersonCommand(person);

        // Verify we can modify the wedding after reopening (no exception)
        CommandResult result = addCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains("Added"));
        assertTrue(model.weddingHasPerson(model.getCurrentWedding(), person));
    }
}
