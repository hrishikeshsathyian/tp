package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.wedding.Wedding;

/**
 * Deletes a Wedding based on provided index
 */
public class DeleteWeddingCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a wedding based on provided index\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Deleted wedding successfully!";
    public static final String MESSAGE_INVALID_INDEX = "The provided wedding index does not exist!";

    private final Index targetIndex;

    public DeleteWeddingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        boolean isInvalidIndex = targetIndex.getZeroBased() >= model.getFilteredWeddingList().size();
        if (isInvalidIndex) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Wedding weddingToDelete = model.getFilteredWeddingList().get(targetIndex.getZeroBased());
        model.deleteWedding(weddingToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteWeddingCommand)) {
            return false;
        }

        DeleteWeddingCommand otherDeleteCommand = (DeleteWeddingCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

}
