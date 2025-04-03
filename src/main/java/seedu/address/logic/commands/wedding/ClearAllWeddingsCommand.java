package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.LogicMemory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;

/**
 * Clears all weddings from the current wedding planner
 */
public class ClearAllWeddingsCommand extends Command {
    public static final String COMMAND_WORD = "clearallweddings";
    public static final String CONFIRMATION_MESSAGE = "Are you sure you want to delete all weddings?"
            + " Type 'y' to proceed and 'n' to cancel deletion";
    public static final String MESSAGE_NO_WEDDINGS = "There are no weddings to be cleared!";

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        if (model.isWeddingPlannerEmpty()) {
            throw new CommandException(MESSAGE_NO_WEDDINGS);
        }

        LogicMemory.setClearingWeddingPlanner(true);

        return new CommandResult(CONFIRMATION_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof ClearAllWeddingsCommand;
    }
}
