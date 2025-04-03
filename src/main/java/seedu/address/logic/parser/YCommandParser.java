package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.YCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class YCommandParser implements Parser<YCommand> {
    public YCommand parse(String args) throws ParseException {
        String trimArgs = args.trim();
        if (!trimArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, YCommand.MESSAGE_USAGE));
        }
        return new YCommand();
    }
}
