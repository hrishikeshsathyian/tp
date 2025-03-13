package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;


/**
 * Adds a Person to a Wedding Object
 */
public class AddWeddingPersonCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Person successfully added to Wedding";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists inside the wedding";
    private final Wedding wedding;
    private final Person toAdd;

    /**
     * Every field must be present and not null.
     */
    public AddWeddingPersonCommand(Wedding wedding, Person toAdd) {
        requireAllNonNull(wedding, toAdd);
        this.wedding = wedding;
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.weddingHasPerson(wedding, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addWeddingPerson(wedding, toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }
}
