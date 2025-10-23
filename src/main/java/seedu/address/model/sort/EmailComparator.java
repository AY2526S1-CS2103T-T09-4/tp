package seedu.address.model.sort;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Compares two {@code Person} objects for sorting purposes based on their {@code Email}.
 * The comparison is done lexicographically based on the string value of the Email.
 */
public class EmailComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getEmail().value.compareTo(person2.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof EmailComparator;
    }
}
