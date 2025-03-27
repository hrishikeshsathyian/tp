package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.WeddingModel;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Finds all persons in a wedding with the specified tag and displays them. The tag string must be an exact match
 * (case-sensitive)
 */
public class FilterByTagCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all members with the specific tag "
            + "(case-sensitive)\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " guest";

    private final PersonContainsTagPredicate predicate;

    public FilterByTagCommand(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(WeddingModel model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
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
