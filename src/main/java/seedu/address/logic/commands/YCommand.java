package seedu.address.logic.commands;

import seedu.address.logic.LogicMemory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;

public class YCommand extends Command {
    public static final String COMMAND_WORD = "y";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Confirms the currently executing task\n"
            + "Usage: y";
    public static final String MESSAGE_NO_COMMANDS_EXECUTING = "No pending operations";
    public static final String MESSAGE_SUCCESS_CLEARALLWEDDINGS = "Successfully cleared all weddings";

    public YCommand() {}

    public CommandResult execute(WeddingModel model) throws CommandException {
        if (LogicMemory.isClearingWeddingPlanner) {
            model.clearAllWeddings();
            LogicMemory.resetLogicMemory();
            return new CommandResult(MESSAGE_SUCCESS_CLEARALLWEDDINGS);
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
