package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * Edits a person in the currently active wedding (draft or open).
 */
public class EditWeddingPersonCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a person in the active wedding identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NO_ACTIVE_WEDDING = "No active wedding! Open a current wedding first.";
    public static final String MESSAGE_INDEX_OUT_OF_RANGE = "The person index provided is invalid";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the wedding.";
    public static final String MESSAGE_CANNOT_EDIT_BRIDE_GROOM_TAGS = "Cannot edit tags for bride or groom.";
    public static final String MESSAGE_CANNOT_ADD_BRIDE_GROOM_TAG = "Cannot add bride or groom tag to a regular member."
            + "Please use the appropriate commands to set bride/groom roles.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditWeddingPersonCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        // Check if there's an active wedding
        Wedding activeWedding = model.getDraftWedding() != null
                ? model.getDraftWedding()
                : model.getCurrentWedding();

        if (activeWedding == null) {
            throw new CommandException(MESSAGE_NO_ACTIVE_WEDDING);
        }

        // Make sure the index is valid
        if (index.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(MESSAGE_INDEX_OUT_OF_RANGE);
        }

        Person personToEdit = model.getFilteredPersonList().get(index.getZeroBased());
        Person editedPerson = createEditPerson(personToEdit, editPersonDescriptor);

        // Check if the person is bride or groom
        boolean isBride = personToEdit.getTags().stream()
                .anyMatch(tag -> tag.tagName.equalsIgnoreCase("bride"));
        boolean isGroom = personToEdit.getTags().stream()
                .anyMatch(tag -> tag.tagName.equalsIgnoreCase("groom"));

        // Check if tags are being modified
        boolean isTagsModified = editPersonDescriptor.getTags().isPresent();

        // Prevent editing tags for bride or groom
        if ((isBride || isGroom) && isTagsModified) {
            throw new CommandException(MESSAGE_CANNOT_EDIT_BRIDE_GROOM_TAGS);
        }

        // Check if trying to add bride/groom tag to a regular member
        if (isTagsModified) {
            boolean tryingToAddBrideTag = editPersonDescriptor.getTags().get().stream()
                    .anyMatch(tag -> tag.tagName.equalsIgnoreCase("bride"));
            boolean tryingToAddGroomTag = editPersonDescriptor.getTags().get().stream()
                    .anyMatch(tag -> tag.tagName.equalsIgnoreCase("groom"));

            if (tryingToAddBrideTag || tryingToAddGroomTag) {
                throw new CommandException(MESSAGE_CANNOT_ADD_BRIDE_GROOM_TAG);
            }
        }

        // Check if the edited person would result in a duplicate
        if (!personToEdit.isSamePerson(editedPerson) && model.weddingHasPerson(activeWedding, editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        // Handle editing based on role
        int personIndex = index.getZeroBased();
        if (personIndex == 0 && activeWedding.getBride() != null
                && activeWedding.getBride().equals(personToEdit)) {
            // Editing bride
            activeWedding.setBride(editedPerson);
        } else if (personIndex == 1 && activeWedding.getGroom() != null
                && activeWedding.getGroom().equals(personToEdit)) {
            // Editing groom
            activeWedding.setGroom(editedPerson);
        } else {
            // Editing a regular member
            activeWedding.getMembers().setPerson(personToEdit, editedPerson);
        }

        // Update model state
        if (model.hasDraftWedding()) {
            model.setDraftWedding(activeWedding);
            if (activeWedding.isValid()) {
                model.commitDraftWedding();
            }
        } else {
            model.setCurrentWedding(activeWedding);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());

        // Special handling for bride/groom - preserve their tags
        boolean isBrideOrGroom = personToEdit.getTags().stream()
                .anyMatch(tag -> tag.tagName.equalsIgnoreCase("bride")
                        || tag.tagName.equalsIgnoreCase("groom"));

        // If person is bride/groom, always use original tags, otherwise use updated tags
        Set<Tag> updatedTags = isBrideOrGroom
                ? personToEdit.getTags()
                : editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditWeddingPersonCommand)) {
            return false;
        }

        EditWeddingPersonCommand otherEditCommand = (EditWeddingPersonCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
