package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wedding.OpenWeddingCommand;

public class OpenWeddingCommandParserTest {
    private OpenWeddingCommandParser parser = new OpenWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsOpenWeddingCommand() {
        // Index 1
        try {
            parser.parse("1");
        } catch (Exception e) {
            fail("Parsing index 1 should not fail: " + e.getMessage());
        }

        // Index 1 with whitespace
        try {
            parser.parse("  1  ");
        } catch (Exception e) {
            fail("Parsing index 1 with whitespace should not fail: " + e.getMessage());
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Non-numeric argument
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenWeddingCommand.MESSAGE_USAGE));

        // Negative index
        assertParseFailure(parser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenWeddingCommand.MESSAGE_USAGE));

        // Zero index
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenWeddingCommand.MESSAGE_USAGE));

        // Empty argument
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenWeddingCommand.MESSAGE_USAGE));
    }
}