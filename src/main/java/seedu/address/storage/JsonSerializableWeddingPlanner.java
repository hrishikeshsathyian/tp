package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWeddingPlanner;
import seedu.address.model.WeddingPlanner;
import seedu.address.model.wedding.Wedding;

/**
 * An Immutable WeddingPlanner that is serializable to JSON format.
 */
@JsonRootName(value = "weddingplanner")
public class JsonSerializableWeddingPlanner {
    public static final String MESSAGE_DUPLICATE_PERSON = "Wedding list contains duplicate wedding(s).";

    private final List<JsonAdaptedWedding> weddings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWeddingPlanner} with the given persons.
     */
    @JsonCreator
    public JsonSerializableWeddingPlanner(@JsonProperty("weddings") List<JsonAdaptedWedding> weddings) {

        this.weddings.addAll(weddings);
        System.out.println("test1");
    }

    /**
     * Converts a given {@code ReadOnlyWeddingPlanner} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWeddingPlanner}.
     */
    public JsonSerializableWeddingPlanner(ReadOnlyWeddingPlanner source) {
        weddings.addAll(source.getWeddingList().stream().map(JsonAdaptedWedding::new).collect(Collectors.toList()));
    }

    /**
     * Converts this wedding planner into the model's {@code WeddingPlanner} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WeddingPlanner toModelType() throws IllegalValueException {
        WeddingPlanner weddingPlanner = new WeddingPlanner();
        for (JsonAdaptedWedding jsonAdaptedWedding : weddings) {
            Wedding wedding = jsonAdaptedWedding.toModelType();
            if (weddingPlanner.hasWedding(wedding)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            weddingPlanner.addWedding(wedding);
        }
        return weddingPlanner;
    }
}
