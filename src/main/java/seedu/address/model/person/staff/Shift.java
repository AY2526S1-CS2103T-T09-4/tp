package seedu.address.model.person.staff;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import seedu.address.logic.parser.DateParser;

/**
 * Represents a staff's shift in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Shift {
    public static final String MESSAGE_FORMAT_CONSTRAINTS = "Invalid date format for shift,"
            + " Expected format: d/M/yyyy";
    public static final String MESSAGE_DUPLICATE_CONSTRAINTS = "Duplicates dates for shifts are not allowed: ";
    public static final String MESSAGE_OLD_CONSTRAINTS = "Invalid shift date as this date has passed: ";

    private final LocalDate shiftDate;

    /**
     * Constructor that creates Shift class with shiftDate.
     */
    public Shift(LocalDate shiftDate) {
        requireAllNonNull(shiftDate);
        this.shiftDate = shiftDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Shift)) {
            return false;
        }

        Shift otherShift = (Shift) other;
        return shiftDate.equals(otherShift.shiftDate);
    }


    @Override
    public String toString() {
        return shiftDate.format(DateParser.FORMATTER);
    }
}

