package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
public class JsonAdaptedWedding {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";
    private final String title;
    private final String date;
    private final JsonAdaptedPerson bride;
    private final JsonAdaptedPerson groom;
    private final List<JsonAdaptedPerson> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given wedding details.
     */
    @JsonCreator
    public JsonAdaptedWedding(@JsonProperty("title") String title, @JsonProperty("date") String date,
                             @JsonProperty("bride") JsonAdaptedPerson bride,
                              @JsonProperty("groom") JsonAdaptedPerson groom,
                             @JsonProperty("members") List<JsonAdaptedPerson> members) {
        this.title = title;
        this.date = date;
        this.bride = bride;
        this.groom = groom;
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        title = source.getTitle().toString();
        date = source.getDate().toString();
        bride = new JsonAdaptedPerson(source.getBride());
        groom = new JsonAdaptedPerson(source.getGroom());
        members.addAll(source.getMembers().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedPerson::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {
        final UniquePersonList modelMembers = new UniquePersonList();
        for (JsonAdaptedPerson person : this.members) {
            modelMembers.add(person.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (groom == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        final Person modelGroom = groom.toModelType();

        if (bride == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        final Person modelBride = bride.toModelType();
        return new Wedding(modelDate, modelTitle, modelBride, modelGroom, modelMembers);
    }
}
