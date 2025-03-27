package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} contains any of the substrings given.
 * It matches parts of words, not just whole words.
 */
public class NameContainsSubstringPredicate implements Predicate<Person> {
    private final List<String> substring;

    public NameContainsSubstringPredicate(List<String> substring) {
        this.substring = substring;
    }

    @Override
    public boolean test(Person person) {
        String personName = person.getName().fullName.toLowerCase();

        return substring.stream()
                .anyMatch(keyword -> personName.contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsSubstringPredicate)) {
            return false;
        }

        NameContainsSubstringPredicate otherPredicate = (NameContainsSubstringPredicate) other;
        return substring.equals(otherPredicate.substring);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("substring", substring).toString();
    }
}
