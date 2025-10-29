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

        char firstChar1 = name1.charAt(0);
        char firstChar2 = name2.charAt(0);
        char lowerFirstChar1 = Character.toLowerCase(firstChar1);
        char lowerFirstChar2 = Character.toLowerCase(firstChar2);
        String name1Rest = name1.substring(1);
        String name2Rest = name2.substring(1);

        if (lowerFirstChar1 != lowerFirstChar2) {
            return name1.compareToIgnoreCase(name2);
        }

        return (firstChar2 - firstChar1) * name1Rest.compareToIgnoreCase(name2Rest);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof NameComparator;
    }
}
