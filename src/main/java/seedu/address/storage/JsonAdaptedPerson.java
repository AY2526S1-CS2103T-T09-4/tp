package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Days;
import seedu.address.model.person.supplier.Items;
import seedu.address.model.person.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final Person.ContactType type;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String note;
    private final List<JsonAdaptedShift> shifts = new ArrayList<>();
    private final List<JsonAdaptedItems> items = new ArrayList<>();
    private final List<JsonAdaptedDays> days = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("type") Person.ContactType type, @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("shifts") List<JsonAdaptedShift> shifts,
                             @JsonProperty("items") List<JsonAdaptedItems> items,
                             @JsonProperty("days") List<JsonAdaptedDays> days,
                             @JsonProperty("note") String note) {
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.note = note;
        if (shifts != null) {
            this.shifts.addAll(shifts);
        }
        if (items != null) {
            this.items.addAll(items);
        }
        if (days != null) {
            this.days.addAll(days);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        this.type = source.getDisplayType();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        note = source.getNote().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        shifts.addAll(source.getShifts().stream()
                .map(JsonAdaptedShift::new)
                .collect(Collectors.toList()));
        items.addAll(source.getItems().stream()
                .map(JsonAdaptedItems::new)
                .collect(Collectors.toList()));
        days.addAll(source.getDays().stream()
                .map(JsonAdaptedDays::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Shift> modelShifts = new ArrayList<>();
        final List<Items> modelItems = new ArrayList<>();
        final List<Days> modelDays = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        for (JsonAdaptedShift shift : shifts) {
            modelShifts.add(shift.toModelType());
        }

        for (JsonAdaptedDays day : days) {
            modelDays.add(day.toModelType());
        }

        for (JsonAdaptedItems item : items) {
            modelItems.add(item.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }

        final Note modelNote = new Note(note);

        switch (type) {
        case CUSTOMER:
            return new Customer(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelNote);
        case STAFF:
            return new Staff(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelShifts, modelNote);
        case SUPPLIER:
            return new Supplier(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelItems, modelDays,
                    modelNote);
        default:
            throw new IllegalValueException("Unexpected contact type: " + type);
        }
    }
}
