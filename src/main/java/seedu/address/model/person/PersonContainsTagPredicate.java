package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

public class PersonContainsTagPredicate implements Predicate<Person> {
    private final Tag tag;

    public PersonContainsTagPredicate(String tag) {
        this.tag = new Tag(tag);
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsTagPredicate)) {
            return false;
        }

        PersonContainsTagPredicate OtherPersonContainsTagPredicate = (PersonContainsTagPredicate) other;
        return tag.equals(OtherPersonContainsTagPredicate.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tag", tag).toString();
    }
}
