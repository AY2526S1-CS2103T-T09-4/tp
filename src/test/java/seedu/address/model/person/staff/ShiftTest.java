package seedu.address.model.person.staff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ShiftTest {

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Shift(null));
    }

    @Test
    public void constructor_validDate_success() {
        LocalDate date = LocalDate.of(2025, 10, 15);
        Shift shift = new Shift(date);

        assertEquals("15/10/2025", shift.toString());
    }

    @Test
    public void toString_returnsIsoFormat() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Shift shift = new Shift(date);

        assertEquals("1/1/2023", shift.toString());
    }
}
