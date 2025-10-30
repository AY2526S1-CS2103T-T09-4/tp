package seedu.address.model.person.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        assertEquals("15/10/2025", days.toString());
    }

    @Test
    public void toString_returnsIsoFormat() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        Days days = new Days(date);

        assertEquals("1/1/2023", days.toString());
    }

    @Test
    public void equals_sameInstance_true() {
        Days d = new Days(LocalDate.of(2025, 10, 30));
        assertTrue(d.equals(d));
    }

    @Test
    public void equals_null_false() {
        Days d = new Days(LocalDate.of(2025, 10, 30));
        assertFalse(d.equals(null));
    }

    @Test
    public void equals_differentType_false() {
        Days d = new Days(LocalDate.of(2025, 10, 30));
        Object notDays = "2025-10-30";
        assertFalse(d.equals(notDays));
    }

    @Test
    public void equals_sameDate_true() {
        LocalDate d = LocalDate.of(2030, 10, 30);
        Days a = new Days(d);
        Days b = new Days(LocalDate.of(2030, 10, 30));
        assertEquals(a, b);
    }

    @Test
    public void equals_differentDate_false() {
        Days a = new Days(LocalDate.of(2030, 10, 30));
        Days b = new Days(LocalDate.of(2030, 11, 2));
        assertNotEquals(a, b);
    }
}
