package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicMemory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.WeddingModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;

public class AddWeddingPersonCommandTest {

    private WeddingModel model;
    private Wedding testWedding;

    @BeforeEach
    public void setUp() {
        model = new WeddingModelManager();
        testWedding = new Wedding(new Date("25122025"), new Title("John & Mary"));
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddWeddingPersonCommand(null));
    }

    @Test
    public void execute_noActiveWedding_throwsCommandException() {
        // Create a person to add and create the command
        Person person = new PersonBuilder().build();
        AddWeddingPersonCommand command = new AddWeddingPersonCommand(person);

        // Execute the command and expect an exception
        assertThrows(
                CommandException.class,
                AddWeddingPersonCommand.MESSAGE_NO_ACTIVE_WEDDING, () -> command.execute(model)
        );
    }

    @Test
    public void execute_addPersonToDraftWedding_success() throws Exception {
        // Set up a draft wedding
        model.setDraftWedding(testWedding);

        // Create a person to add
        Person person = new PersonBuilder().build();

        // Update the draft stage to be waiting for bride
        LogicMemory.setDraftStage(LogicMemory.DraftState.ADDING_BRIDE);

        // Create and execute the command
        AddWeddingPersonCommand command = new AddWeddingPersonCommand(person);

        // Verify the result
        assertThrows(
                CommandException.class,
                "You have a wedding being created!\n"
                        + "Add the bride using : add n/NAME p/PHONE e/EMAIL a/ADDRESS t/bride",
                () -> command.execute(model)
        );

        LogicMemory.resetLogicMemory();
    }

    @Test
    public void execute_addPersonToCurrentWedding_success() throws Exception {
        // Add wedding to storage and set as current
        model.addWedding(testWedding);
        model.setCurrentWedding(testWedding);

        // Create a person to add
        Person person = new PersonBuilder().build();

        // Create and execute the command
        AddWeddingPersonCommand command = new AddWeddingPersonCommand(person);
        CommandResult result = command.execute(model);

        // Verify the result
        assertTrue(result.getFeedbackToUser().contains("Added"));
        assertTrue(model.weddingHasPerson(model.getCurrentWedding(), person));
    }

    @Test
    public void execute_addBrideWithTag_success() throws Exception {
        // Set up a draft wedding
        model.setDraftWedding(testWedding);

        // Create a person with bride tag
        Person person = new PersonBuilder().withTags("bride").build();

        // Update the draft stage to be waiting for bride
        LogicMemory.setDraftStage(LogicMemory.DraftState.ADDING_BRIDE);

        // Create and execute the command
        AddWeddingPersonCommand command = new AddWeddingPersonCommand(person);
        CommandResult result = command.execute(model);

        // Verify the result
        assertTrue(result.getFeedbackToUser().contains("bride"));
        assertEquals(person, model.getDraftWedding().getBride());

        LogicMemory.resetLogicMemory();
    }

    @Test
    public void execute_addGroomWithTag_success() throws Exception {
        // Set up a draft wedding
        model.setDraftWedding(testWedding);

        // Create a person with groom tag
        Person person = new PersonBuilder().withName("John").withTags("groom").build();

        // Update the draft stage to be waiting for groom
        LogicMemory.setDraftStage(LogicMemory.DraftState.ADDING_GROOM);

        // Create and execute the command
        AddWeddingPersonCommand command = new AddWeddingPersonCommand(person);
        CommandResult result = command.execute(model);

        // Verify the result
        assertTrue(result.getFeedbackToUser().contains("groom"));

        LogicMemory.resetLogicMemory();
    }

    @Test
    public void execute_addSecondBride_throwsCommandException() throws Exception {
        // Set up a draft wedding
        model.setDraftWedding(testWedding);
        LogicMemory.setDraftStage(LogicMemory.DraftState.ADDING_BRIDE);
        // Add first bride
        Person firstBride = new PersonBuilder().withName("Mary").withTags("bride").build();
        new AddWeddingPersonCommand(firstBride).execute(model);


        // Try to add second bride
        Person secondBride = new PersonBuilder().withName("Jane").withTags("bride").build();
        AddWeddingPersonCommand command = new AddWeddingPersonCommand(secondBride);

        // Execute the command and expect an exception
        assertThrows(CommandException.class,
                "You have a wedding being created!\n"
                        + "Add the groom using : add n/NAME p/PHONE e/EMAIL a/ADDRESS t/groom",
                () -> command.execute(model)
        );

        LogicMemory.resetLogicMemory();
    }

    @Test
    public void execute_addBrideAndGroom_commitsValidWedding() throws Exception {
        // Set up a draft wedding
        model.setDraftWedding(testWedding);
        LogicMemory.setDraftStage(LogicMemory.DraftState.ADDING_BRIDE);
        // Add bride
        Person bride = new PersonBuilder().withName("Mary").withTags("bride").build();
        new AddWeddingPersonCommand(bride).execute(model);

        // Add groom
        Person groom = new PersonBuilder().withName("John").withTags("groom").build();
        new AddWeddingPersonCommand(groom).execute(model);

        // Verify the draft is now valid and was committed
        assertFalse(model.hasDraftWedding()); // Draft should be moved to permanent storage
        assertTrue(model.hasWedding(testWedding)); // Should be in permanent storage

        // Verify the wedding has both bride and groom
        Wedding storedWedding = model.getFilteredWeddingList().get(0);
        assertNotNull(storedWedding.getBride());
        assertNotNull(storedWedding.getGroom());
        assertEquals(bride, storedWedding.getBride());
        assertEquals(groom, storedWedding.getGroom());

        LogicMemory.resetLogicMemory();
    }
}
