package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWeddingPlanner;

public interface WeddingPlannerStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getWeddingPlannerFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyWeddingPlanner> readWeddingPlanner() throws DataLoadingException;

    /**
     * @see #getWeddingPlannerFilePath() ()
     */
    Optional<ReadOnlyWeddingPlanner> readWeddingPlanner(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyWeddingPlanner} to the storage.
     * @param weddingPlanner cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner) throws IOException;

    /**
     * @see #saveWeddingPlanner(ReadOnlyWeddingPlanner) (ReadOnlyAddressBook)
     */
    void saveWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner, Path filePath) throws IOException;
}
