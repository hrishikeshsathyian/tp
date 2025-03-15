package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.WeddingModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model) throws CommandException {
        throw new UnsupportedOperationException("This command does not support execution on a Model.");
    }

    /**
     * Executes the command on a WeddingModel.
     *
     * @param model {@code WeddingModel} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(WeddingModel model) throws CommandException {
        throw new UnsupportedOperationException("This command does not support execution on a WeddingModel.");
    }
}

