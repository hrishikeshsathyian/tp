package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class RemoveWeddingPersonCommandTest {

    private WeddingModel model;
    private Wedding wedding;
    private Person bride;
    private Person groom;
    private Person guest1;
    private Person guest2;

    @BeforeEach
    public void setUp() {
        model = new WeddingModelManager();
        wedding = new Wedding(new Date("25122025"), new Title("John & Mary"));

        // Create dummy persons for bride, groom, and guests.
        bride = new PersonBuilder().withName("Mary").withTags("bride").build();
        groom = new PersonBuilder().withName("John").withTags("groom").build();
        guest1 = new PersonBuilder().withName("Alice").build();
        guest2 = new PersonBuilder().withName("Bob").build();

        // Set the bride and groom in the wedding.
        wedding.setBride(bride);
        wedding.setGroom(groom);
        // Add additional wedding members.
        wedding.addMember(guest1);
        wedding.addMember(guest2);

        // Set the current wedding to be active.
        model.setCurrentWedding(wedding);
    }


    @Test
    public void execute_indexOutOfRange_throwsCommandException() {
        // Expecting 4 persons in the filtered list: bride, groom, guest1, guest2.
        // Using an index of 5 should be out of range.
        RemoveWeddingPersonCommand command = new RemoveWeddingPersonCommand(Index.fromOneBased(5));
        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(RemoveWeddingPersonCommand.MESSAGE_INDEX_OUT_OF_RANGE, exception.getMessage());
    }

    @Test
    public void execute_removeBrideOrGroom_throwsCommandException() {
        // Bride should be at index 1.
        RemoveWeddingPersonCommand commandBride = new RemoveWeddingPersonCommand(Index.fromOneBased(1));
        CommandException exceptionBride = assertThrows(CommandException.class, () -> commandBride.execute(model));
        assertEquals(RemoveWeddingPersonCommand.MESSAGE_REMOVE_BRIDE_OR_GROOM, exceptionBride.getMessage());

        // Groom should be at index 2.
        RemoveWeddingPersonCommand commandGroom = new RemoveWeddingPersonCommand(Index.fromOneBased(2));
        CommandException exceptionGroom = assertThrows(CommandException.class, () -> commandGroom.execute(model));
        assertEquals(RemoveWeddingPersonCommand.MESSAGE_REMOVE_BRIDE_OR_GROOM, exceptionGroom.getMessage());
    }

    @Test
    public void execute_validRemovalFromCurrentWedding_success() throws Exception {
        // Expected ordering: 1: bride, 2: groom, 3: guest1, 4: guest2.
        // Remove guest1 at index 3.
        RemoveWeddingPersonCommand command = new RemoveWeddingPersonCommand(Index.fromOneBased(3));
        CommandResult result = command.execute(model);

        // Verify the success message.
        assertTrue(result.getFeedbackToUser().contains(RemoveWeddingPersonCommand.MESSAGE_SUCCESS));
        // Verify that guest1 is removed from the wedding.
        assertFalse(wedding.hasPerson(guest1));
        // Verify that the filtered person list no longer contains guest1.
        assertFalse(model.getFilteredPersonList().contains(guest1));
    }

    @Test
    public void execute_validRemovalFromDraftWedding_success() throws Exception {
        // Set the draft wedding to be active.
        model.setDraftWedding(wedding);

        // Expected ordering remains: 1: bride, 2: groom, 3: guest1, 4: guest2.
        // Remove guest2 at index 4.
        RemoveWeddingPersonCommand command = new RemoveWeddingPersonCommand(Index.fromOneBased(4));
        CommandResult result = command.execute(model);

        // Verify the success message.
        assertTrue(result.getFeedbackToUser().contains(RemoveWeddingPersonCommand.MESSAGE_SUCCESS));
        // Verify that guest2 is removed from the wedding.
        assertFalse(wedding.hasPerson(guest2));
    }
}
