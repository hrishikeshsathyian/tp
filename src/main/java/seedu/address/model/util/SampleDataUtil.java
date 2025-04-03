package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWeddingPlanner;
import seedu.address.model.WeddingPlanner;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Title;
import seedu.address.model.wedding.Wedding;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Wedding[] getSampleWeddings() {
        Wedding[] sampleWeddings = new Wedding[2];
        Wedding weddingOne = new Wedding(new Date("09062025"), new Title("Jason and Wendy"),
                new Person(new Name("Wendy"), new Phone("99764735"), new Email("wendy@gmail.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("bride")),
                new Person(new Name("Jason"), new Phone("98764735"), new Email("jason@gmail.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("groom")),
                new UniquePersonList());
        Wedding weddingTwo = new Wedding(new Date("21072025"), new Title("Daniel and Denise"),
                new Person(new Name("Denise"), new Phone("97665844"), new Email("denise@gmail.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("bride")),
                new Person(new Name("Daniel"), new Phone("67891234"), new Email("daniel@gmail.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("groom")),
                new UniquePersonList());
        sampleWeddings[0] = weddingOne;
        sampleWeddings[1] = weddingTwo;
        return sampleWeddings;
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyWeddingPlanner getSampleWeddingPlanner() {
        WeddingPlanner sampleWp = new WeddingPlanner();
        for (Wedding sampleWedding : getSampleWeddings()) {
            sampleWp.addWedding(sampleWedding);
        }
        return sampleWp;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
