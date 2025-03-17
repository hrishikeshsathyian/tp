package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;

/**
 * Closes the currently open wedding
 */
public class CloseWeddingCommand extends Command {
    public static final String COMMAND_WORD = "closewedding";
    public static final String MESSAGE_SUCCESS = "Closed current wedding";
    public static final String MESSAGE_NO_OPEN_WEDDING = "No wedding is currently open";

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCurrentWedding()) {
            throw new CommandException(MESSAGE_NO_OPEN_WEDDING);
        }

        model.clearCurrentWedding();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
