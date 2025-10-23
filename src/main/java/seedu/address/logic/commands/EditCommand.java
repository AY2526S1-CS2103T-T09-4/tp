package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FIELD_ENTERED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.customer.Points;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Days;
import seedu.address.model.person.supplier.Items;
import seedu.address.model.person.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: \n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */

    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null;

        Person.ContactType contactType = personToEdit.getContactType();
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Note updatedNote = editPersonDescriptor.getNote().orElse(personToEdit.getNote());
        Points updatedPoints = editPersonDescriptor.getPoints().orElse(personToEdit.getPoints());
        List<Shift> updatedShifts = editPersonDescriptor.getShifts().orElse(personToEdit.getShifts());
        List<Items> updatedItems = editPersonDescriptor.getItems().orElse(personToEdit.getItems());
        List<Days> updatedDays = editPersonDescriptor.getDays().orElse(personToEdit.getDays());

        switch (contactType) {
        case CUSTOMER:
            if (!isNull(updatedShifts)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_SHIFTS));
            }
            if (!isNull(updatedItems)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_ITEMS));
            }
            if (!isNull(updatedDays)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_DAYS));
            }
            return new Customer(updatedName,
                    updatedPhone, updatedEmail,
                    updatedAddress, updatedPoints,
                    updatedTags, updatedNote);

        case STAFF:
            if (!isNull(updatedPoints)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_POINTS));
            }
            if (!isNull(updatedItems)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_ITEMS));
            }
            if (!isNull(updatedDays)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_DAYS));
            }
            return new Staff(updatedName,
                    updatedPhone, updatedEmail,
                    updatedAddress, updatedTags,
                    updatedShifts, updatedNote);
        case SUPPLIER:
            if (!isNull(updatedPoints)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_POINTS));
            }
            if (!isNull(updatedShifts)) {
                throw new CommandException(String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_SHIFTS));
            }
            return new Supplier(updatedName,
                    updatedPhone, updatedEmail,
                    updatedAddress, updatedTags,
                    updatedItems, updatedDays,
                    updatedNote);
        default:
            return personToEdit;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
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
        private Person.ContactType contactType;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Note note;

        // Customers
        private Points points;

        // Supplier
        private List<Items> items;
        private List<Days> days;

        // Staff
        private List<Shift> shifts;

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
            setNote(toCopy.note);
            setPoints(toCopy.points);
            setShifts(toCopy.shifts);
            setItems(toCopy.items);
            setDays(toCopy.days);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, note, items, days, shifts, points);
        }

        public void setContactType(Person.ContactType contactType) {
            this.contactType = contactType;
        }

        public Person.ContactType getContactType() {
            return this.contactType;
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

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setPoints(Points points) {
            this.points = points;
        }

        public Optional<Points> getPoints() {
            return Optional.ofNullable(points);
        }

        public void setShifts(List<Shift> shifts) {
            this.shifts = shifts;
        }

        public Optional<List<Shift>> getShifts() {
            return Optional.ofNullable(shifts);
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public Optional<List<Items>> getItems() {
            return Optional.ofNullable(items);
        }

        public void setDays(List<Days> days) {
            this.days = days;
        }

        public Optional<List<Days>> getDays() {
            return Optional.ofNullable(days);
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
            return Objects.equals(contactType, otherEditPersonDescriptor.contactType)
                    && Objects.equals(name, otherEditPersonDescriptor.name)
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
