package seedu.address.model.sort;

import seedu.address.model.person.Person;

import java.util.Comparator;

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
