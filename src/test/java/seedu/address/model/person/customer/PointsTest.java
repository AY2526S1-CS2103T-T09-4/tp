package seedu.address.model.person.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PointsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Points(null));
    }

    @Test
    public void isValidPoints() {
        // null email
        assertThrows(NullPointerException.class, () -> Points.isValidPoints(null));

        // blank points
        assertFalse(Points.isValidPoints("")); // empty string
        assertFalse(Points.isValidPoints(" ")); // spaces only

        // invalid characters
        assertFalse(Points.isValidPoints("a"));
        assertFalse(Points.isValidPoints("@"));
        assertFalse(Points.isValidPoints("/"));

        // invalid numbers
        assertFalse(Points.isValidPoints("-1"));
        assertFalse(Points.isValidPoints("0.5"));
    }

    @Test
    public void equals() {
        Points points = new Points(3);

        // same values -> returns true
        assertTrue(points.equals(new Points(3)));

        // same object -> returns true
        assertTrue(points.equals(points));

        // null -> returns false
        assertFalse(points.equals(null));

        // different types -> returns false
        assertFalse(points.equals(5.0f));

        // different values -> returns false
        assertFalse(points.equals(new Points(1)));
    }

}
