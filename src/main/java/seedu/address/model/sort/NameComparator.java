package seedu.address.model.sort;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Compares two {@code Person} objects for sorting purposes based on their {@code Name}.
 * The comparison is done lexicographically based on the string value of the Name.
 */
public class NameComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        String name1 = person1.getName().fullName;
        String name2 = person2.getName().fullName;

        if (name1.isEmpty() && name2.isEmpty()) {
            return 0;
        }

        if (name1.isEmpty()) {
            return -1;
        }
        if (name2.isEmpty()) {
            return 1;
        }

        char char1 = name1.charAt(0);
        char char2 = name2.charAt(0);

        if (Character.toUpperCase(char1) == Character.toUpperCase(char2)) {
            boolean isLower1 = Character.isLowerCase(char1);
            boolean isLower2 = Character.isLowerCase(char2);

            if (isLower1 && !isLower2) {
                return -1;
            }

            if (!isLower1 && isLower2) {
                return 1;
            }
        }

        return name1.compareToIgnoreCase(name2);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof NameComparator;
    }
}
