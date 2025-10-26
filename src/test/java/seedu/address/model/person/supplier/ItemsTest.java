package seedu.address.model.person.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemsTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Items(null));
    }

    @Test
    public void constructor_validName_success() {
        Items item = new Items("egg");
        assertEquals("egg", item.toString());
    }

    @Test
    public void toString_returnsRawName() {
        Items item = new Items("milk");
        assertEquals("milk", item.toString());
    }
}
