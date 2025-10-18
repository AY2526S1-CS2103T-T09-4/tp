package seedu.address.model.sort;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a CompareType to sort address book.
 * Guarantees: immutable; type is valid as declared in {@link #isValidCompareType(String)}
 */
public class CompareType {

    public static final String MESSAGE_INVALID_CONTACT_TYPE = "Invalid comparison type. Can only be 'name' or 'type'.";
    public static final List<String> VALIDATION_LIST = List.of("name", "type");

    private final String compareTypeString;

    /**
     * Constructs a {@code CompareType}.
     *
     * @param compareType A valid type name.
     */
    public CompareType(String compareType) {
        requireNonNull(compareType);
        checkArgument(isValidCompareType(compareType), MESSAGE_INVALID_CONTACT_TYPE);
        this.compareTypeString = compareType.trim().toLowerCase();;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidCompareType(String test) {
        return VALIDATION_LIST.contains(test.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompareType)) {
            return false;
        }

        CompareType otherCompareType = (CompareType) other;
        return compareTypeString.equals(otherCompareType.compareTypeString);
    }

    @Override
    public int hashCode() {
        return compareTypeString.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return compareTypeString;
    }


    public Comparator<Person> toComparator(boolean isAsc) throws ParseException {
        switch (compareTypeString) {
        case "name":
            return new AlphanumericNameComparator(isAsc);
        case "type":
            return new ContactTypeComparator(isAsc);
        default:
            throw new ParseException(CompareType.MESSAGE_INVALID_CONTACT_TYPE);
        }
    }
}
