package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.wedding.AddWeddingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;

/**
 * Parses input arguments and creates a new AddWeddingCommand object
 */
public class AddWeddingCommandParser implements Parser<AddWeddingCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddWeddingCommandParser.class);

    @Override
    public AddWeddingCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Parsing AddWeddingCommand: {0}", args);

        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddWeddingCommand.MESSAGE_USAGE));
            }

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE);

            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_NAME).get());
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

            logger.log(Level.INFO, "Successfully parsed wedding draft - Title: {0}, Date: {1}",
                new Object[]{title, date});

            return new AddWeddingCommand(new Wedding(date, title));
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Invalid AddWeddingCommand format: " + args, pe);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddWeddingCommand.MESSAGE_USAGE), pe);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
