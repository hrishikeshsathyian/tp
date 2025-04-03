package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.wedding.CloseWeddingCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the CloseWeddingCommand
 */
public class CloseWeddingCommandParser implements Parser<CloseWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * CloseWeddingCommand and returns a CloseWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloseWeddingCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseWeddingCommand.MESSAGE_USAGE));
        }
        return new CloseWeddingCommand();
    }
}
