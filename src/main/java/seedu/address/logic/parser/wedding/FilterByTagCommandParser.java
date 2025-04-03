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

    public static final String TOO_MANY_TAGS_MESSAGE = "The filter command only accepts a single tag. "
            + "Please provide exactly one tag.";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterByTagCommand
     * and returns a FilterByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterByTagCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();

        // Check if there are multiple words (tags) by splitting by whitespace
        String[] words = trimArgs.split("\\s+");
        if (words.length > 1) {
            throw new ParseException(TOO_MANY_TAGS_MESSAGE);
        }


        if (trimArgs.isEmpty()) {
            return new FilterByTagCommand();
        } else {
            return new FilterByTagCommand(new PersonContainsTagPredicate(trimArgs));
        }
    }
}
