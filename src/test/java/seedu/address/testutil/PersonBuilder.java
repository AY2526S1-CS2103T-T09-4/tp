package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * An abstract utility class to help with building Person objects.
 */
public abstract class PersonBuilder<T extends Person> {

    public static final String DEFAULT_NAME = CommandTestUtil.VALID_NAME_AMY;
    public static final String DEFAULT_PHONE = CommandTestUtil.VALID_PHONE_AMY;
    public static final String DEFAULT_EMAIL = CommandTestUtil.VALID_EMAIL_AMY;
    public static final String DEFAULT_ADDRESS = CommandTestUtil.VALID_ADDRESS_AMY;
    public static final String DEFAULT_TAG = CommandTestUtil.VALID_TAG_AMY;
    public static final String DEFAULT_NOTE = CommandTestUtil.VALID_NOTE_AMY;

    // Default fields
    protected Name name;
    protected Phone phone;
    protected Email email;
    protected Address address;
    protected Set<Tag> tags;
    protected Note note;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        note = new Note("");
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(T personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        note = personToCopy.getNote();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder<T> withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder<T> withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder<T> withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder<T> withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder<T> withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder<T> withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    public abstract T build();

    public PersonBuilder<T> withShifts(String ... shifts) {
        return this;
    }

    public PersonBuilder<T> withItems(String ... items) {
        return this;
    }

    public PersonBuilder<T> withDays(String ... days) {
        return this;
    }
}
