package seedu.address.model.person.supplier;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import seedu.address.logic.parser.DateParser;

/**
 * Represents a supplier's supplying days in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Days {
    public static final String MESSAGE_FORMAT_CONSTRAINTS = "Invalid date format for day, Expected format: d/M/yyyy";
    public static final String MESSAGE_DUPLICATE_CONSTRAINTS = "Duplicates dates for days are not allowed: ";
    public static final String MESSAGE_OLD_CONSTRAINTS = "Invalid days date as this date has passed: ";

    private final LocalDate daysSupplied;

    /**
     * Constructor that creates Days class.
     */
    public Days(LocalDate daysSupplied) {
        requireAllNonNull(daysSupplied);
        this.daysSupplied = daysSupplied;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Days)) {
            return false;
        }

        Days otherDays = (Days) other;
        return daysSupplied.equals(otherDays.daysSupplied);
    }

    @Override
    public String toString() {
        return daysSupplied.format(DateParser.FORMATTER);
    }
}

