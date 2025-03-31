package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.WeddingModelManager;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;

public class DeleteWeddingCommandTest {

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
        // When no weddings are added, any index should be invalid.
        DeleteWeddingCommand command = new DeleteWeddingCommand(Index.fromOneBased(1));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        // Add a single wedding, then attempt to delete using an index that is out-of-bounds.
        model.addWedding(wedding1);
        DeleteWeddingCommand command = new DeleteWeddingCommand(Index.fromOneBased(2)); // Only one wedding exists
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_validIndex_deletesWedding() throws Exception {
        // Add a wedding then delete it using a valid index.
        model.addWedding(wedding1);
        DeleteWeddingCommand command = new DeleteWeddingCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        // Verify command feedback and that the wedding is removed from the filtered list.
        assertEquals(DeleteWeddingCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(model.getFilteredWeddingList().isEmpty());
    }

    @Test
    public void execute_multipleWeddings_deletesCorrectWedding() throws Exception {
        // Add multiple weddings.
        model.addWedding(wedding1);
        model.addWedding(wedding2);

        // Delete the first wedding.
        DeleteWeddingCommand command = new DeleteWeddingCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        // Verify the deletion was successful.
        assertEquals(DeleteWeddingCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        // Only one wedding should remain and it should be wedding2.
        assertEquals(1, model.getFilteredWeddingList().size());
        assertEquals(wedding2, model.getFilteredWeddingList().get(0));
    }
}
