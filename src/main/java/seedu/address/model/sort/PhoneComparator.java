package seedu.address.model.sort;

import seedu.address.model.person.Person;

import java.util.Comparator;

public class PhoneComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getPhone().value.compareTo(person2.getPhone().value);
    }
}
