package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a Wedding to the Wedding Planner
 */
public class AddWeddingCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Wedding successfully created!";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists.";
    private final Wedding toAdd;

    /**
     * Every field must be present and not null.
     */
    public AddWeddingCommand(Wedding toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasWedding(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        model.addWedding(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatWedding(toAdd)));
    }
}
