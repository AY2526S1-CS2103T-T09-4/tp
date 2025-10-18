package seedu.address.model.sort;

import seedu.address.model.person.Person;
import seedu.address.model.person.customer.Customer;

import java.util.Comparator;

public class ContactTypeComparator implements Comparator<Person> {
    private final boolean isAsc;

    public ContactTypeComparator(boolean isAsc) {
        this.isAsc = isAsc;
    }

    @Override
    public int compare(Person person1, Person person2) {
        Person.ContactType person1Type;
        Person.ContactType person2Type;

        person1Type = person1.getContactType();
        person2Type = person2.getContactType();

        int typeComparison = person1Type.compareTo(person2Type);

        int result;
        if (typeComparison == 0) {
            result = new AlphanumericNameComparator(isAsc).compare(person1, person2);
        } else {
            result = typeComparison;
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactTypeComparator)) {
            return false;
        }

        ContactTypeComparator otherComparator = (ContactTypeComparator) other;
        return this.isAsc == otherComparator.isAsc;
    }
}
