package seedu.address.logic.commands.wedding;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.model.wedding.Wedding;

/**
 * Finds all persons in a wedding with the specified tag and displays them. The tag string must be an exact match
 * (case-sensitive)
 */
public class FilterByTagCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_NO_ACTIVE_WEDDING = "No active wedding! Create or open a wedding first.";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all members with the specific tag "
            + "(case-sensitive)\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " guest";

    private final PersonContainsTagPredicate predicate;

    public FilterByTagCommand(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    public FilterByTagCommand() {
        this.predicate = null;
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

        if (!isNull(predicate)) {
            model.updateFilteredPersonList(predicate);
        } else {
            model.updateFilteredPersonList();
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterByTagCommand)) {
            return false;
        }

        FilterByTagCommand otherFilterCommand = (FilterByTagCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}
