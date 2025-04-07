package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.wedding.DeleteWeddingCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the DeleteWeddingCommand.
 */
public class DeleteWeddingCommandParser implements Parser<DeleteWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteWeddingCommand and returns a DeleteWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWeddingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteWeddingCommand(index);
        } catch (ParseException pe) {
            boolean argIsNotAnInteger = pe.getMessage().equals(ParserUtil.MESSAGE_INVALID_INDEX_NON_INTEGER);
            if (argIsNotAnInteger) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWeddingCommand.MESSAGE_USAGE), pe);
            } else {
                throw new ParseException(
                        String.format(DeleteWeddingCommand.MESSAGE_INVALID_INDEX)
                );
            }
        }
    }
}
