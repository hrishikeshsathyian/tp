package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.wedding.Wedding;

/**
 * Opens a specific wedding for editing
 */
public class OpenWeddingCommand extends Command {
    public static final String COMMAND_WORD = "openwedding";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Opens a wedding for editing\n"
        + "Parameters: INDEX\n"
        + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Now editing wedding: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The wedding index is invalid";

    private final Index targetIndex;

    public OpenWeddingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getFilteredWeddingList().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Wedding weddingToOpen = model.getFilteredWeddingList().get(targetIndex.getZeroBased());
        model.setCurrentWedding(weddingToOpen);
        return new CommandResult(String.format(MESSAGE_SUCCESS, weddingToOpen));
    }
}
