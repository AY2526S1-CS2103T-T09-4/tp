package seedu.address.model.person.customer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a customer's loyalty points in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Points {
    public static final String MESSAGE_CONSTRAINTS = "Points should be an integer that is greater than or equals to 0.";

    public final Integer value;

    /**
     * Constructor that creates Points class with shiftDate.
     */
    public Points(Integer points) {
        requireAllNonNull(points);
        this.value = points;
    }

    /**
     * Checks whether a given string is a valid input to parse into a Points.
     */
    public static boolean isValidPoints(String points) {
        int numPoints;
        try {
            numPoints = Integer.parseInt(points);
        } catch (NumberFormatException e) {
            return false;
        }

        return numPoints >= 0;
    }

    public static int parsePoints(String points) {
        if (!isValidPoints(points)) {
            return -1;
        }
        return Integer.parseInt(points);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Points)) {
            return false;
        }

        Points otherPoints = (Points) other;
        return value.equals(otherPoints.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

