package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.NCommand;
import seedu.address.logic.commands.YCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.wedding.AddWeddingPersonCommand;
import seedu.address.logic.parser.WeddingPlannerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyWeddingPlanner;
import seedu.address.model.WeddingModel;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final WeddingModel model;
    private final Storage storage;
    private final WeddingPlannerParser weddingPlannerParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(WeddingModel model, Storage storage) { // Changed parameter type from Model to WeddingModel
        this.model = model;
        this.storage = storage;
        weddingPlannerParser = new WeddingPlannerParser();
    }

    private boolean isCommandAllowedInDrafting(Command command) {
        return command instanceof AddWeddingPersonCommand
                || command instanceof ExitCommand
                || command instanceof NCommand;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = weddingPlannerParser.parseCommand(commandText);

        if (LogicMemory.checkIfDrafting() && !isCommandAllowedInDrafting(command)) {
            throw new CommandException(LogicMemory.getDraftingMessage());
        }

        boolean isCancellingClearWedding = (LogicMemory.isClearingWeddingPlanner()
                && !(command instanceof YCommand || command instanceof NCommand));
        if (isCancellingClearWedding) {
            LogicMemory.resetLogicMemory();
        }

        commandResult = command.execute(model);

        try {

            storage.saveWeddingPlanner(model.getWeddingPlanner());

        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        } catch (NullPointerException e) {
            System.out.println("dsljfskljdf");
        }

        return commandResult;
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyWeddingPlanner getWeddingPlanner() {
        return model.getWeddingPlanner();
    }

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return model.getFilteredWeddingList();
    }

    @Override
    public Path getWeddingPlannerFilePath() {
        return model.getWeddingPlannerFilePath();
    }
}
