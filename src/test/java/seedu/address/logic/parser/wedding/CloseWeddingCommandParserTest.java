package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wedding.CloseWeddingCommand;

public class CloseWeddingCommandParserTest {
    private CloseWeddingCommandParser parser = new CloseWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsCloseWeddingCommand() {
        // empty arguments
        try {
            parser.parse("");
        } catch (Exception e) {
            fail("Parsing empty string should not fail: " + e.getMessage());
        }

        // arguments with whitespace
        try {
            parser.parse("   ");
        } catch (Exception e) {
            fail("Parsing whitespace should not fail: " + e.getMessage());
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // non-empty arguments
        assertParseFailure(parser, "some args",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseWeddingCommand.MESSAGE_SUCCESS));

        // numeric arguments
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseWeddingCommand.MESSAGE_SUCCESS));
    }
}
