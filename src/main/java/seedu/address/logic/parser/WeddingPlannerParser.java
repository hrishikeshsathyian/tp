package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NCommand;
import seedu.address.logic.commands.SortWeddingCommand;
import seedu.address.logic.commands.YCommand;
import seedu.address.logic.commands.wedding.AddWeddingCommand;
import seedu.address.logic.commands.wedding.AddWeddingPersonCommand;
import seedu.address.logic.commands.wedding.ClearAllWeddingsCommand;
import seedu.address.logic.commands.wedding.CloseWeddingCommand;
import seedu.address.logic.commands.wedding.DeleteWeddingCommand;
import seedu.address.logic.commands.wedding.EditWeddingPersonCommand;
import seedu.address.logic.commands.wedding.FilterByTagCommand;
import seedu.address.logic.commands.wedding.FindPersonCommand;
import seedu.address.logic.commands.wedding.OpenWeddingCommand;
import seedu.address.logic.commands.wedding.RemoveWeddingPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.wedding.AddWeddingCommandParser;
import seedu.address.logic.parser.wedding.AddWeddingPersonCommandParser;
import seedu.address.logic.parser.wedding.ClearAllWeddingsCommandParser;
import seedu.address.logic.parser.wedding.CloseWeddingCommandParser;
import seedu.address.logic.parser.wedding.DeleteWeddingCommandParser;
import seedu.address.logic.parser.wedding.EditWeddingPersonCommandParser;
import seedu.address.logic.parser.wedding.FilterByTagCommandParser;
import seedu.address.logic.parser.wedding.FindPersonCommandParser;
import seedu.address.logic.parser.wedding.OpenWeddingCommandParser;
import seedu.address.logic.parser.wedding.RemoveWeddingPersonCommandParser;

/**
 * Parses user input.
 */
public class WeddingPlannerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(WeddingPlannerParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case DeleteWeddingCommand.COMMAND_WORD:
            return new DeleteWeddingCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddWeddingCommand.COMMAND_WORD:
            return new AddWeddingCommandParser().parse(arguments);

        case AddWeddingPersonCommand.COMMAND_WORD:
            return new AddWeddingPersonCommandParser().parse(arguments);

        case OpenWeddingCommand.COMMAND_WORD:
            return new OpenWeddingCommandParser().parse(arguments);

        case CloseWeddingCommand.COMMAND_WORD:
            return new CloseWeddingCommandParser().parse(arguments);

        case SortWeddingCommand.COMMAND_WORD:
            return new SortWeddingCommand();

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments);

        case RemoveWeddingPersonCommand.COMMAND_WORD:
            return new RemoveWeddingPersonCommandParser().parse(arguments);

        case FilterByTagCommand.COMMAND_WORD:
            return new FilterByTagCommandParser().parse(arguments);

        case ClearAllWeddingsCommand.COMMAND_WORD:
            return new ClearAllWeddingsCommandParser().parse(arguments);

        case YCommand.COMMAND_WORD:
            return new YCommandParser().parse(arguments);

        case NCommand.COMMAND_WORD:
            return new NCommandParser().parse(arguments);

        case EditWeddingPersonCommand.COMMAND_WORD:
            return new EditWeddingPersonCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
