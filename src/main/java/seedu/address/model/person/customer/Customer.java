package seedu.address.model.person.customer;

import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a customer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Customer extends Person {

    /**
     * Constructor that calls super class constructor
     */
    public Customer(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Note note) {
        super(name, phone, email, address, tags, note);
    }

    @Override
    public ContactType getDisplayType() {
        return ContactType.CUSTOMER;
    }
}
