package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.WeddingModel.PREDICATE_SHOW_ALL_WEDDINGS;

import seedu.address.model.Model;
import seedu.address.model.WeddingModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all weddings!";


    @Override
    public CommandResult execute(WeddingModel model) {
        requireNonNull(model);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
