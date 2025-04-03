package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.NCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class NCommandParser implements Parser<NCommand> {
    public NCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();
        if (!trimArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NCommand.MESSAGE_USAGE));
        }
        return new NCommand();
    }
}
