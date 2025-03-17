package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.wedding.CloseWeddingCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the CloseWeddingCommand
 */
public class CloseWeddingCommandParser implements Parser<CloseWeddingCommand> {

    public CloseWeddingCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseWeddingCommand.MESSAGE_SUCCESS));
        }
        return new CloseWeddingCommand();
    }
}
