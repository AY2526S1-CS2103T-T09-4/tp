package seedu.address.model.sort;

import seedu.address.model.person.Person;

import java.util.Comparator;

public class AlphanumericNameComparator implements Comparator<Person> {
    private final boolean isAsc;

    public AlphanumericNameComparator(boolean isAsc) {
        this.isAsc = isAsc;
    }

    @Override
    public int compare(Person person1, Person person2) {
        int result = person1.getName().fullName.compareToIgnoreCase(person2.getName().fullName);
        return isAsc ? result : -result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AlphanumericNameComparator)) {
            return false;
        }

        AlphanumericNameComparator otherComparator = (AlphanumericNameComparator) other;
        return this.isAsc == otherComparator.isAsc;
    }
}
