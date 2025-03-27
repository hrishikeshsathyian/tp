package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.person.NameContainsSubstringPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Finds and lists all weddings in the wedding planner that have members whose names contain any of
 * the argument substrings.
 * Substring matching is case insensitive.
 */
public class FindMemberCommand extends Command {

    public static final String COMMAND_WORD = "findmember";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all weddings containing members whose names "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " jerry je Jerry";

    public static final String MESSAGE_NO_MATCHING_MEMBERS = "No members found with the given keywords.";
    public static final String MESSAGE_MEMBERS_FOUND_OVERVIEW = "Found %1$d wedding(s) with matching members.";

    private final NameContainsSubstringPredicate predicate;

    public FindMemberCommand(NameContainsSubstringPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        List<Wedding> allWeddings = model.getWeddingPlanner().getWeddingList();
        List<Wedding> matchedWeddings = new ArrayList<>();
        StringBuilder resultMessage = new StringBuilder();

        // Track total matches for the summary
        int totalMatches = 0;

        // Check if any member matches the predicate for each wedding
        for (Wedding wedding : allWeddings) {
            boolean foundMatch = false;
            List<Person> matchedMembers = new ArrayList<>();

            // Check bride
            if (wedding.getBride() != null && predicate.test(wedding.getBride())) {
                matchedMembers.add(wedding.getBride());
                foundMatch = true;
            }

            // Check groom
            if (wedding.getGroom() != null && predicate.test(wedding.getGroom())) {
                matchedMembers.add(wedding.getGroom());
                foundMatch = true;
            }

            // Check other members
            if (wedding.getMembers() != null) {
                for (Person member : wedding.getMembers().asUnmodifiableObservableList()) {
                    if (predicate.test(member)) {
                        matchedMembers.add(member);
                        foundMatch = true;
                    }
                }
            }

            // Add matchFound to results
            if (foundMatch) {
                matchedWeddings.add(wedding);
                totalMatches += matchedMembers.size();

                // Add wedding details
                resultMessage.append("\nWedding: ").append(wedding.getTitle())
                        .append(" (").append(wedding.getDate()).append(")\n");
                resultMessage.append("Matched members: ");

                for (Person member : matchedMembers) {
                    resultMessage.append(member.getName().fullName).append(", ");
                }

                // Remove trailing comma and space
                if (!matchedMembers.isEmpty()) {
                    resultMessage.delete(resultMessage.length() - 2, resultMessage.length());
                }
                resultMessage.append("\n");
            }
        }

        // Update model to display matched weddings among all weddings
        model.updateFilteredWeddingList(wedding -> matchedWeddings.contains(wedding));

        if (matchedWeddings.isEmpty()) {
            return new CommandResult(MESSAGE_NO_MATCHING_MEMBERS);
        }

        String returnMessage = String.format(MESSAGE_MEMBERS_FOUND_OVERVIEW, matchedWeddings.size())
                + "\nTotal matching members found: " + totalMatches + "\n";
        return new CommandResult(returnMessage + resultMessage.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindMemberCommand)) {
            return false;
        }

        FindMemberCommand otherFindMemberCommand = (FindMemberCommand) other;
        return predicate.equals(otherFindMemberCommand.predicate);
    }
}
