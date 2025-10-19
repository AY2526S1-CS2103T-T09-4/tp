package seedu.address.model.sort;

import seedu.address.model.person.Person;

import java.util.Comparator;

public class AddressComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getAddress().value.compareTo(person2.getAddress().value);
    }
}