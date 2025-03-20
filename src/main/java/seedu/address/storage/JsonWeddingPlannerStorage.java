package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyWeddingPlanner;

/**
 * A class to access WeddingPlanner data stored as a json file on the hard disk.
 */
public class JsonWeddingPlannerStorage implements WeddingPlannerStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonWeddingPlannerStorage.class);

    private Path filePath;

    public JsonWeddingPlannerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWeddingPlannerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWeddingPlanner> readWeddingPlanner() throws DataLoadingException {
        return readWeddingPlanner(filePath);
    }

    /**
     * Similar to {@link #readWeddingPlanner()} d()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyWeddingPlanner> readWeddingPlanner(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableWeddingPlanner> jsonWeddingPlanner = JsonUtil.readJsonFile(
                filePath, JsonSerializableWeddingPlanner.class);
        if (!jsonWeddingPlanner.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWeddingPlanner.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner) throws IOException {
        saveWeddingPlanner(weddingPlanner, filePath);
    }

    /**
     * Similar to {@link #saveWeddingPlanner(ReadOnlyWeddingPlanner)} (ReadOnlyWeddingPlanner)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWeddingPlanner(ReadOnlyWeddingPlanner weddingPlanner, Path filePath) throws IOException {
        requireNonNull(weddingPlanner);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWeddingPlanner(weddingPlanner), filePath);
    }
}
