package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.YCommand;
import seedu.address.logic.commands.wedding.ClearAllWeddingsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse arguments and return a new ClearAllWeddingsCommand object for execution
 */
public class ClearAllWeddingsCommandParser implements Parser<ClearAllWeddingsCommand> {
    /**
     * Parse arguments and returns a new ClearAllWeddingsCommand object for execution
     * @return ClearAllWeddingsCommand
     * @throws ParseException if any arguments are provided (command does not take any arguments)
     */
    public ClearAllWeddingsCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();
        if (!trimArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, YCommand.MESSAGE_USAGE));
        }
        return new ClearAllWeddingsCommand();
    };
}
