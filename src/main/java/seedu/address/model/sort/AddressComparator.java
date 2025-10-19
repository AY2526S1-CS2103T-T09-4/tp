package seedu.address.model.sort;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Compares two {@code Person} objects for sorting purposes based on their {@code Address}.
 * The comparison is done lexicographically based on the string value of the address.
 */
public class AddressComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getAddress().value.compareTo(person2.getAddress().value);
    }
}
