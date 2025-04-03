package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.YCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments and returns a new YCommand object
 */
public class YCommandParser implements Parser<YCommand> {
    /**
     * Parses arguments and returns a new YCommand object for execution (no arguments are needed in this case)
     * @return YCommand
     * @throws ParseException if any arguments are provided (command does not take any arguments)
     */
    public YCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();
        if (!trimArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, YCommand.MESSAGE_USAGE));
        }
        return new YCommand();
    }
}
