package seedu.address.model.sort;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Compares two {@code Person} objects for sorting purposes based on their {@code Phone}.
 * The comparison is done lexicographically based on the string value of the Phone.
 */
public class PhoneComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getPhone().value.compareTo(person2.getPhone().value);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PhoneComparator;
    }
}
