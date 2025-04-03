package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.wedding.AddWeddingCommand;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;

public class AddWeddingCommandParserTest {
    private AddWeddingCommandParser parser = new AddWeddingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Wedding expectedWedding = new Wedding(new Date("25122025"), new Title("John & Mary"));

        // normal case
        assertParseSuccess(parser, " n/John & Mary d/25122025",
                new AddWeddingCommand(expectedWedding));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWeddingCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, " d/25122025", expectedMessage);

        // missing date prefix
        assertParseFailure(parser, " n/John & Mary", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date (wrong format)
        String dateErrorMessage = "Invalid command format! \n"
                + "new: Creates a wedding draft\n"
                + "Parameters: n/WEDDING_NAME d/DATE\n"
                + "Example: new n/John & Mary d/25122025";
        assertParseFailure(parser, " n/John & Mary d/01302020", dateErrorMessage);
    }
}
