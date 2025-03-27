package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.WeddingModel;

/**
 * Sorts all weddings by date.
 */
public class SortWeddingCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all weddings by date!";

    @Override
    public CommandResult execute(WeddingModel model) {
        requireNonNull(model);
        model.sortWeddingList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
