package seedu.address.model.person.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    public void equals_sameDate_true() {
        LocalDate d = LocalDate.of(2025, 10, 30);
        Days a = new Days(d);
        Days b = new Days(LocalDate.of(2025, 10, 30));
        assertEquals(a, b);
    }

    @Test
    public void hashCode_sameDate_equal() {
        LocalDate d = LocalDate.of(2025, 10, 30);
        Days a = new Days(d);
        Days b = new Days(LocalDate.of(2025, 10, 30));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals_differentDate_false() {
        Days a = new Days(LocalDate.of(2025, 10, 30));
        Days b = new Days(LocalDate.of(2025, 11, 2));
        assertNotEquals(a, b);
    }
}
