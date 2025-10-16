package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.CommandTestUtil;
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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = CommandTestUtil.VALID_NAME_AMY;
    public static final String DEFAULT_PHONE = CommandTestUtil.VALID_PHONE_AMY;
    public static final String DEFAULT_EMAIL = CommandTestUtil.VALID_EMAIL_AMY;
    public static final String DEFAULT_ADDRESS = CommandTestUtil.VALID_ADDRESS_AMY;
    public static final String DEFAULT_TAG = CommandTestUtil.VALID_TAG_AMY;
    public static final String DEFAULT_NOTE = CommandTestUtil.VALID_NOTE_AMY;

    // Default fields
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Note note;

    // Fields for staff
    private List<Shift> shifts;

    // Fields for suppliers
    private Days days;
    private Items items;

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
    public PersonBuilder(Person personToCopy) {
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
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code Shift} of the {@code Person} that we are building (for staff).
     */
    public PersonBuilder withShifts(List<Shift> shifts) {
        // TODO
        return this;
    }

    /**
     * Sets the {@code Days} of the {@code Person} that we are building (for supplier).
     */
    public PersonBuilder withDays(LocalDate days) {
        // TODO
        return this;
    }

    /**
     * Sets the {@code Items} of the {@code Person} that we are building (for supplier).
     */
    public PersonBuilder withItems(String items) {
        // TODO
        return this;
    }

    /**
     * Builds a new {@code Customer} by taking in the relevant fields and outputting an object.
     */
    public Customer buildCustomer() {
        return new Customer(name, phone, email, address, tags, note);
    }

    /**
     * Builds a new {@code Staff} by taking in the relevant fields and outputting an object.
     */
    public Staff buildStaff() {
        // TODO
        return null;
    }

    /**
     * Builds a new {@code Supplier} by taking in the relevant fields and outputting an object.
     */
    public Supplier buildSupplier() {
        // TODO
        return null;
    }
}
