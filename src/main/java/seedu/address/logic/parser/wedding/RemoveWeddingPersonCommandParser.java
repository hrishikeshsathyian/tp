package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.wedding.RemoveWeddingPersonCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the RemoveWeddingPersonCommand.
 */
public class RemoveWeddingPersonCommandParser implements Parser<RemoveWeddingPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RemoveWeddingPersonCommand and returns a RemoveWeddingPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveWeddingPersonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveWeddingPersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveWeddingPersonCommand.MESSAGE_USAGE), pe);
        }
    }
}
