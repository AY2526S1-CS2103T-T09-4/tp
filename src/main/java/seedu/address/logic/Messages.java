package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_FIELD_ENTERED = "Invalid field entered. %1$s is not valid for this "
            + "contact type.";
    public static final String MESSAGE_CLEAR_COMMAND_CHECK = "To clear all contacts, type 'clear' alone.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Notes: ")
                .append(person.getNote())
                .append("; Tags: ");

        person.getTags().forEach(builder::append);
        switch(person.getContactType()) {
        case CUSTOMER:
            builder.append("; Points: ")
                    .append(person.getPoints());
            break;

        case STAFF:
            builder.append("; Shifts: ")
                    .append(person.getShifts());
            break;
        case SUPPLIER:
            builder.append("; items: ")
                    .append(person.getItems())
                    .append("; days: ")
                    .append(person.getDays());
            break;
        default:
        }


        return builder.toString();
    }

}
