package seedu.address.logic.parser.wedding;

import seedu.address.logic.commands.wedding.ClearAllWeddingsCommand;
import seedu.address.logic.parser.Parser;

public class ClearAllWeddingsCommandParser implements Parser<ClearAllWeddingsCommand> {
    @Override
    public ClearAllWeddingsCommand parse(String args) {
        return new ClearAllWeddingsCommand();
    };
}
