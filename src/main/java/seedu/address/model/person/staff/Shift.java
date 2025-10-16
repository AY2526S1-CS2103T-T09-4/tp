package seedu.address.model.person.staff;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

/**
 * Represents a staff's shift in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Shift {
    public static final String MESSAGE_COMPULSORY = "At least one shift must be provided";
    public static final String MESSAGE_CONSTRAINTS = "Invalid date format for shift, Expected format: yyyy-MM-dd";

    private final LocalDate shiftDate;

    /**
     * Constructor that creates Shift class with shiftDate.
     */
    public Shift(LocalDate shiftDate) {
        requireAllNonNull(shiftDate);
        this.shiftDate = shiftDate;
    }

    @Override
    public String toString() {
        return shiftDate.toString();
    }
}

