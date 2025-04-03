package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.wedding.FilterByTagCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagPredicate;


/**
 * Parses input arguments and creates a new FilterByTagCommand object
 */
public class FilterByTagCommandParser implements Parser<FilterByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterByTagCommand
     * and returns a FilterByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterByTagCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();
        String regex = "[,\\.\\s]";
        String[] argsArray = trimArgs.split(regex);
        boolean isArgsValid = argsArray.length > 1;
        if (isArgsValid) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTagCommand.MESSAGE_USAGE));
        }

        if (trimArgs.isEmpty()) {
            return new FilterByTagCommand();
        } else {
            return new FilterByTagCommand(new PersonContainsTagPredicate(trimArgs));
        }
    }
}
