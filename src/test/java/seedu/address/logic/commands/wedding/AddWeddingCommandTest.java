package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.WeddingModelManager;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;

public class AddWeddingCommandTest {

    private WeddingModel model;
    private Wedding testWedding;
    private Wedding testWedding2;

    @BeforeEach
    public void setUp() {
        model = new WeddingModelManager();
        testWedding = new Wedding(new Date("25122025"), new Title("John & Mary"));
        testWedding2 = new Wedding(new Date("31122025"), new Title("Jack & Kelly"));
    }

    @Test
    public void constructor_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddWeddingCommand(null));
    }

    @Test
    public void execute_newWedding_success() throws Exception {
        // Create and execute the command
        AddWeddingCommand command = new AddWeddingCommand(testWedding);
        CommandResult result = command.execute(model);

        // Verify the result
        assertEquals(AddWeddingCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(testWedding, model.getDraftWedding());
        assertTrue(model.hasDraftWedding());
    }

    @Test
    public void execute_existingDraftWedding_overwritesAndShowsMessage() throws Exception {
        // Setup existing draft
        model.setDraftWedding(testWedding2);

        // Create and execute command
        AddWeddingCommand command = new AddWeddingCommand(testWedding);
        CommandResult result = command.execute(model);

        // Verify the result shows overwrite message
        assertTrue(result.getFeedbackToUser().contains(AddWeddingCommand.MESSAGE_EXISTING_DRAFT));
        assertEquals(testWedding, model.getDraftWedding());
    }

    @Test
    public void execute_duplicateWedding_throwsCommandException() throws Exception {
        // crate a wedding
        model.addWedding(testWedding);

        // Try to create the same wedding
        AddWeddingCommand command = new AddWeddingCommand(testWedding);

        // Execute the command and expect an exception
        assertThrows(
                CommandException.class,
                AddWeddingCommand.MESSAGE_DUPLICATE_WEDDING,
                () -> command.execute(model)
        );
    }

    @Test
    public void execute_differentButValidWeddings_success() throws Exception {
        // First command execution
        CommandResult result1 = new AddWeddingCommand(testWedding).execute(model);
        assertEquals(AddWeddingCommand.MESSAGE_SUCCESS, result1.getFeedbackToUser());

        // Commit the first wedding
        model.commitDraftWedding();

        // Verify it's in permanent storage
        assertTrue(model.hasWedding(testWedding));

        // Create a second, different wedding
        CommandResult result2 = new AddWeddingCommand(testWedding2).execute(model);
        assertEquals(AddWeddingCommand.MESSAGE_SUCCESS, result2.getFeedbackToUser());

        // Verify draft is correctly set
        assertEquals(testWedding2, model.getDraftWedding());
    }

    @Test
    public void equals() {
        Date date1 = new Date("25122025");
        Title title1 = new Title("John & Mary");
        Wedding wedding1 = new Wedding(date1, title1);

        Date date2 = new Date("31122025");
        Title title2 = new Title("Jack & Kelly");
        Wedding wedding2 = new Wedding(date2, title2);

        AddWeddingCommand addWeddingCommand1 = new AddWeddingCommand(wedding1);
        AddWeddingCommand addWeddingCommand2 = new AddWeddingCommand(wedding2);

        // same object -> returns true
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1));

        // same values -> returns true
        AddWeddingCommand addWeddingCommand1Copy = new AddWeddingCommand(wedding1);
        assertTrue(addWeddingCommand1.equals(addWeddingCommand1Copy));

        // different types -> returns false
        assertFalse(addWeddingCommand1.equals(1));

        // null -> returns false
        assertFalse(addWeddingCommand1.equals(null));

        // different wedding -> returns false
        assertFalse(addWeddingCommand1.equals(addWeddingCommand2));
    }
}
