package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.WeddingModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;

public class OpenWeddingCommandTest {

    private WeddingModel model;
    private Wedding wedding1;
    private Wedding wedding2;

    @BeforeEach
    public void setUp() {
        model = new WeddingModelManager();
        wedding1 = new Wedding(new Date("25122025"), new Title("John & Mary"));
        wedding2 = new Wedding(new Date("31122025"), new Title("Jack & Jill"));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Create the command with an invalid index
        OpenWeddingCommand command = new OpenWeddingCommand(Index.fromOneBased(1)); // No weddings exist

        // Execute the command and expect an exception
        assertThrows(CommandException.class,
                OpenWeddingCommand.MESSAGE_INVALID_INDEX,
                () -> command.execute(model));
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        model.addWedding(wedding1);

        // Create the command with an index that's too high
        OpenWeddingCommand command = new OpenWeddingCommand(Index.fromOneBased(2)); // Only one wedding exists

        assertThrows(CommandException.class,
                OpenWeddingCommand.MESSAGE_INVALID_INDEX,
                () -> command.execute(model));
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        model.addWedding(wedding1);

        // Create the command with a valid index and execute the command
        OpenWeddingCommand command = new OpenWeddingCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        // Verify the result
        assertTrue(result.getFeedbackToUser().contains("Now editing wedding"));
        assertEquals(wedding1, model.getCurrentWedding());
        assertTrue(model.hasCurrentWedding());
    }

    @Test
    public void execute_multipleWeddings_opensCorrectOne() throws Exception {
        // Add multiple weddings
        model.addWedding(wedding1);
        model.addWedding(wedding2);

        // Create a command for the second wedding and execute it
        OpenWeddingCommand command = new OpenWeddingCommand(Index.fromOneBased(2));
        CommandResult result = command.execute(model);

        // Verify the second wedding was opened
        assertTrue(result.getFeedbackToUser().contains("Now editing wedding"));
        assertEquals(wedding2, model.getCurrentWedding());
    }

    @Test
    public void execute_openWeddingWithExistingData_loadsAllData() throws Exception {
        // Add bride and groom to the wedding
        Person bride = new PersonBuilder().withName("Mary").withTags("bride").build();
        Person groom = new PersonBuilder().withName("John").withTags("groom").build();
        Person guest = new PersonBuilder().withName("Guest").build();

        wedding1.setBride(bride);
        wedding1.setGroom(groom);
        wedding1.addMember(guest);

        // Add to model and check it's stored correctly
        model.addWedding(wedding1);

        // Open the wedding
        OpenWeddingCommand command = new OpenWeddingCommand(Index.fromOneBased(1));
        command.execute(model);

        // Verify all data is loaded
        Wedding openedWedding = model.getCurrentWedding();
        assertNotNull(openedWedding.getBride());
        assertNotNull(openedWedding.getGroom());
        assertEquals(bride, openedWedding.getBride());
        assertEquals(groom, openedWedding.getGroom());
        assertTrue(openedWedding.hasPerson(guest));

        // Verify filtered person list is updated
        assertTrue(model.getFilteredPersonList().contains(bride));
        assertTrue(model.getFilteredPersonList().contains(groom));
        assertTrue(model.getFilteredPersonList().contains(guest));
    }

    @Test
    public void execute_switchFromOneWeddingToAnother_success() throws Exception {
        // Add both weddings
        model.addWedding(wedding1);
        model.addWedding(wedding2);

        // Open the first wedding
        new OpenWeddingCommand(Index.fromOneBased(1)).execute(model);
        assertEquals(wedding1, model.getCurrentWedding());

        // Open the second wedding
        CommandResult result = new OpenWeddingCommand(Index.fromOneBased(2)).execute(model);

        // Verify the switch was successful
        assertTrue(result.getFeedbackToUser().contains("Now editing wedding"));
        assertEquals(wedding2, model.getCurrentWedding());
    }
}