package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.Staff;

/**
 * A utility class to help with building Staff objects.
 */
public class StaffBuilder extends PersonBuilder<Staff> {
    public static final String DEFAULT_SHIFTS = CommandTestUtil.VALID_SHIFTS_CARL;

    private List<Shift> shifts;

    /**
     * Creates a {@code StaffBuilder} with the default details.
     */
    public StaffBuilder() {
        super();
        try {
            shifts = ParserUtil.parseShifts(List.of(DEFAULT_SHIFTS));
        } catch (ParseException e) {
            // Fallthrough
        }
    }

    /**
     * Initializes the StaffBuilder with the data of {@code staffToCopy}.
     */
    public StaffBuilder(Staff staffToCopy) {
        super(staffToCopy);
        shifts = staffToCopy.getShifts();
    }

    @Override
    public StaffBuilder withShifts(String... shifts) {
        try {
            this.shifts = ParserUtil.parseShifts(Arrays.asList(shifts));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid shift format in StaffBuilder", e);
        }
        return this;
    }


    /**
     * Builds a new {@code Staff} by taking in the relevant fields and outputting an object.
     */
    public Staff build() {
        return new Staff(name, phone, email, address, tags, shifts, note);
    }
}
