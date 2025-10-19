package seedu.address.model.sort;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Comparator;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Represents a CompareType to sort address book.
 * Guarantees: immutable; type is valid as declared in {@link #isValidCompareType(String)}
 */
public class Attribute {

    public static final List<String> VALIDATION_LIST = List.of("name", "type", "email", "address", "phone");
    public static final String MESSAGE_INVALID_ATTRIBUTE = "Invalid attribute. Can only be"
            + formatValidationList();

    private final String attributeString;

    /**
     * Constructs a {@code CompareType}.
     *
     * @param attributeString A valid type name.
     */
    public Attribute(String attributeString) {
        requireNonNull(attributeString);
        checkArgument(isValidCompareType(attributeString), MESSAGE_INVALID_ATTRIBUTE);
        this.attributeString = attributeString.trim().toLowerCase();;
    }

    private static String formatValidationList() {
        if (Attribute.VALIDATION_LIST.isEmpty()) {
            return "";
        }
        if (Attribute.VALIDATION_LIST.size() == 1) {
            return Attribute.VALIDATION_LIST.get(0);
        }

        String allButLast = String.join(", ", Attribute.VALIDATION_LIST.subList(0,
                Attribute.VALIDATION_LIST.size() - 1));

        return allButLast + ", or " + Attribute.VALIDATION_LIST.get(Attribute.VALIDATION_LIST.size() - 1);
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
        if (!(other instanceof Attribute)) {
            return false;
        }

        Attribute otherAttribute = (Attribute) other;
        return attributeString.equals(otherAttribute.attributeString);
    }

    @Override
    public int hashCode() {
        return attributeString.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return attributeString;
    }

    /**
     * Creates a {@code Comparator<Person>} based on the internal attribute string.
     * It maps recognized attribute names (e.g., "name", "email", "phone") to their
     * corresponding concrete comparator implementations (e.g., {@code NameComparator},
     * {@code EmailComparator}).
     *
     * @return The {@code Comparator<Person>} that provides the specified sorting logic.
     * @throws ParseException If the internal attribute string does not match any
     *     supported comparison attribute (name, type, email, address, or phone).
     */
    public Comparator<Person> toComparator() throws ParseException {
        switch (attributeString) {
        case "name":
            return new NameComparator();
        case "type":
            return new ContactTypeComparator();
        case "email":
            return new EmailComparator();
        case "address":
            return new AddressComparator();
        case "phone":
            return new PhoneComparator();
        default:
            throw new ParseException(Attribute.MESSAGE_INVALID_ATTRIBUTE);
        }
    }
}
