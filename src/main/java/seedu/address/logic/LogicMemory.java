package seedu.address.logic;

/**
 * LogicMemory keeps track of persistent states to enable actions that require multiple commands to run sequentially.
 */
public class LogicMemory {
    /**
     * Keeps track of the 3 stages of adding a wedding -> no wedding being added, waiting for bride, waiting for groom
     */
    public enum DraftState {
        NO_DRAFT,
        ADDING_BRIDE,
        ADDING_GROOM
    }

    private static final String MESSAGE_ADDING_BRIDE = "You have a wedding being created!\n"
            + "Add the bride using : add n/NAME p/PHONE e/EMAIL a/ADDRESS t/bride";

    private static final String MESSAGE_ADDING_GROOM = "You have a wedding being created!\n"
            + "Add the groom using : add n/NAME p/PHONE e/EMAIL a/ADDRESS t/groom";

    private static boolean clearingWeddingPlanner = false;

    private static DraftState addWeddingStage = DraftState.NO_DRAFT;

    //=========== New Wedding Utils ======================================================================
    public static DraftState getDraftStage() {
        return addWeddingStage;
    }

    public static void setDraftStage(DraftState state) {
        addWeddingStage = state;
    }

    public static boolean checkIfDrafting() {
        return addWeddingStage != DraftState.NO_DRAFT;
    }

    public static String getDraftingMessage() {
        if (addWeddingStage == DraftState.ADDING_BRIDE) {
            return MESSAGE_ADDING_BRIDE;
        }
        return MESSAGE_ADDING_GROOM;
    }

    //=========== Clear all Weddings Utils ======================================================================
    public static boolean isClearingWeddingPlanner() {
        return clearingWeddingPlanner;
    }

    public static void setClearingWeddingPlanner(boolean state) {
        clearingWeddingPlanner = state;
    }

    /**
     * Clears the logic memory states and resets all to their original fields
     */
    public static void resetLogicMemory() {
        clearingWeddingPlanner = false;
        addWeddingStage = DraftState.NO_DRAFT;
    }
}
