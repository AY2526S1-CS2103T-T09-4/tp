package seedu.address.model.person.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DaysTest {

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Days(null));
    }

    @Test
    public void constructor_validDate_success() {
        LocalDate date = LocalDate.of(2025, 10, 15);
        Days days = new Days(date);

        assertEquals("2025-10-15", days.toString());
    }

    @Test
    public void toString_returnsIsoFormat() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Days days = new Days(date);

        assertEquals("2023-01-01", days.toString());
    }
}