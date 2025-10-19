package seedu.address.model.sort;

import seedu.address.model.person.Person;

import java.util.Comparator;

public class NameComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.getName().fullName.compareTo(person2.getName().fullName);
    }
}
