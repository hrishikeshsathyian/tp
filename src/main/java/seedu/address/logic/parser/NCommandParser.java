package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.NCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse input and creates a new NCommand object
 */
public class NCommandParser implements Parser<NCommand> {
    /**
     * Parses arguments and returns a new NCommand object for execution (no arguments are needed in this case)
     * @return NCommand
     * @throws ParseException if any arguments are provided (command does not take any arguments)
     */
    public NCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();
        if (!trimArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NCommand.MESSAGE_USAGE));
        }
        return new NCommand();
    }
}
