package seedu.address.model.person.supplier;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

/**
 * Represents a supplier's supplying days in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Days {
    public static final String MESSAGE_COMPULSORY = "At least one day must be provided";
    public static final String MESSAGE_CONSTRAINTS = "Invalid date format for day, Expected format: yyyy-MM-dd";

    private final LocalDate daysSupplied;

    /**
     * Constructor that creates Days class.
     */
    public Days(LocalDate daysSupplied) {
        requireAllNonNull(daysSupplied);
        this.daysSupplied = daysSupplied;
    }

    @Override
    public String toString() {
        return daysSupplied.toString();
    }
}

