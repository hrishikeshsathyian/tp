package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.wedding.Wedding;

/**
 * Removes a person from the currently active wedding (draft or open)
 */
public class RemoveWeddingPersonCommand extends Command {
    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Removes a person from the active wedding\n"
            + "Parameters: "
            + "INDEX \n"
            + "Example: " + COMMAND_WORD + " "
            + "3";

    public static final String MESSAGE_SUCCESS = "Removed Wedding Person successfully!";
    public static final String MESSAGE_NO_ACTIVE_WEDDING = "No active wedding! Create or open a wedding first.";
    public static final String MESSAGE_REMOVE_BRIDE_OR_GROOM = "Every wedding has to have a bride and groom :0 Let's"
                                                        + " not remove the soon to be married couple!";
    public static final String MESSAGE_INDEX_OUT_OF_RANGE = "Please select a valid index to remove!";
    private final Index targetIndex;

    /**
     * Creates an RemoveWeddingPersonCommand to remove the person at the specified {@code Index}
     */
    public RemoveWeddingPersonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        Wedding activeWedding = model.getDraftWedding() != null
                ? model.getDraftWedding()
                : model.getCurrentWedding();

        if (activeWedding == null) {
            throw new CommandException(MESSAGE_NO_ACTIVE_WEDDING);
        }

        int indexToRemove = targetIndex.getOneBased();

        boolean indexOutOfRange = indexToRemove <= 0 || indexToRemove > model.getFilteredPersonList().size();
        if (indexOutOfRange) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_RANGE);
        }

        boolean isAttemptingToRemoveBrideOrGroom = indexToRemove == 1 || indexToRemove == 2;
        if (isAttemptingToRemoveBrideOrGroom) {
            throw new CommandException(MESSAGE_REMOVE_BRIDE_OR_GROOM);
        }

        model.removeWeddingPerson(activeWedding, indexToRemove);

        if (model.hasDraftWedding()) {
            model.setDraftWedding(activeWedding);
            if (activeWedding.isValid()) {
                model.commitDraftWedding();
            }
        } else {
            model.setCurrentWedding(activeWedding);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
