package seedu.address.model.sort;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Compares two {@code Person} objects for sorting purposes based on their {@code ContactType}.
 * The comparison is done lexicographically based on the string value of the ContactType.
 */
public class ContactTypeComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        Person.ContactType person1Type;
        Person.ContactType person2Type;

        person1Type = person1.getContactType();
        person2Type = person2.getContactType();

        int typeComparison = person1Type.compareTo(person2Type);

        if (typeComparison == 0) {
            return new NameComparator().compare(person1, person2);
        }

        return typeComparison;
    }
}
