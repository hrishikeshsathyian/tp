package seedu.address.logic.commands;

import seedu.address.logic.LogicMemory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;

/**
 * No prompt command to abort executing the currently pending task (for 2-step tasks such as clearing all weddings)
 */
public class NCommand extends Command {
    public static final String COMMAND_WORD = "n";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Confirm to abort the pending task\n"
            + "Usage: n";
    public static final String MESSAGE_NO_COMMANDS_EXECUTING = "No pending operations";
    public static final String MESSAGE_SUCCESS_ABORT = "Aborted pending operation";

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        if (LogicMemory.isClearingWeddingPlanner()) {
            LogicMemory.resetLogicMemory();
            return new CommandResult(MESSAGE_SUCCESS_ABORT);
        }

        throw new CommandException(MESSAGE_NO_COMMANDS_EXECUTING);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof YCommand;
    }
}
