package seedu.address.logic.commands;

import seedu.address.model.WeddingModel;

/**
 * Help command that displays the link to the application's User Guide.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_SUCCESS = "Help message displayed";
    public static final String SHOWING_HELP_MESSAGE = "Help window successfully displayed! In case you missed it,"
                        + " refer to this URL for the User Guide: \n https://ay2425s2-cs2103t-w09-4.github.io/tp/";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
                                                + "Example: " + COMMAND_WORD;
    @Override
    public CommandResult execute(WeddingModel model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

}
