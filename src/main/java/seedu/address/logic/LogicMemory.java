package seedu.address.logic;

/**
 * When issuing delete operations (such as RemoveWeddingPerson or DeleteWedding), we want to issue a command to double
 * check that the user is sure they want to delete it. The LogicMemory will keep track of the items to be processed
 * so that the subsequent confirmation or rejection will know what it is supposed to delete.
 */
public class LogicMemory {
    private static boolean clearingWeddingPlanner = false;

    public static boolean isClearingWeddingPlanner() {
        return clearingWeddingPlanner;
    }

    public static void setClearingWeddingPlanner(boolean state) {
        clearingWeddingPlanner = state;
    }

    public static void resetLogicMemory() {
        clearingWeddingPlanner = false;
    }
}
