package seedu.address.model.person.supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a supplier in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Supplier extends Person {

    private final List<Items> items;
    private final List<Days> days;

    /**
     * Constructs a Supplier by calling super class constructor and initialise other relevant fields
     */
    public Supplier(Name name,
                    Phone phone,
                    Email email,
                    Address address,
                    Set<Tag> tags,
                    List<Items> items,
                    List<Days> days,
                    Note note) {
        super(name, phone, email, address, tags, note);
        this.items = new ArrayList<>(items);
        this.days = new ArrayList<>(days);
    }

    public List<Items> getItems() {
        return items;
    }

    public List<Days> getDays() {

        return days;
    }

    @Override
    public ContactType getDisplayType() {
        return ContactType.SUPPLIER;
    }
}
