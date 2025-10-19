package seedu.address.model.person.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.sort.AddressComparator;
import seedu.address.model.sort.Attribute;
import seedu.address.model.sort.ContactTypeComparator;
import seedu.address.model.sort.EmailComparator;
import seedu.address.model.sort.NameComparator;
import seedu.address.model.sort.PhoneComparator;

public class AttributeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attribute(null));
    }

    @Test
    public void constructor_invalidAttribute_throwsIllegalArgumentException() {
        String invalidAttribute = "invalid";
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, () -> new Attribute(invalidAttribute));
        assertTrue(thrown.getMessage().contains(Attribute.MESSAGE_INVALID_ATTRIBUTE));
    }

    @Test
    public void isValidAttribute() {
        assertThrows(NullPointerException.class, () -> Attribute.isValidAttribute(null));

        assertFalse(Attribute.isValidAttribute(""));
        assertFalse(Attribute.isValidAttribute(" "));
        assertFalse(Attribute.isValidAttribute("tag"));
        assertFalse(Attribute.isValidAttribute("Name "));

        assertTrue(Attribute.isValidAttribute("name"));
        assertTrue(Attribute.isValidAttribute("type"));
        assertTrue(Attribute.isValidAttribute("email"));
        assertTrue(Attribute.isValidAttribute("address"));
        assertTrue(Attribute.isValidAttribute("phone"));
        assertTrue(Attribute.isValidAttribute("NaMe"));
    }

    @Test
    public void toComparator_validAttributes_returnsCorrectComparator() throws ParseException {
        Comparator<Person> nameComparator = new NameComparator();
        Comparator<Person> typeComparator = new ContactTypeComparator();
        Comparator<Person> emailComparator = new EmailComparator();
        Comparator<Person> addressComparator = new AddressComparator();
        Comparator<Person> phoneComparator = new PhoneComparator();

        assertEquals(nameComparator, new Attribute("name").toComparator());
        assertEquals(typeComparator, new Attribute("type").toComparator());
        assertEquals(emailComparator, new Attribute("email").toComparator());
        assertEquals(addressComparator, new Attribute("address").toComparator());
        assertEquals(phoneComparator, new Attribute("phone").toComparator());

        assertEquals(nameComparator, new Attribute("NAME").toComparator());
    }

    @Test
    public void equals() {
        Attribute nameAttribute = new Attribute("name");

        // same object -> returns true
        assertTrue(nameAttribute.equals(nameAttribute));

        // same values -> returns true
        Attribute nameAttributeCopy = new Attribute("name");
        assertTrue(nameAttribute.equals(nameAttributeCopy));

        // same value with different case -> returns true (due to constructor logic)
        Attribute nameAttributeCase = new Attribute("NAME");
        assertTrue(nameAttribute.equals(nameAttributeCase));

        // different types -> returns false
        assertFalse(nameAttribute.equals(1));

        // null -> returns false
        assertFalse(nameAttribute.equals(null));

        // different attribute -> returns false
        Attribute phoneAttribute = new Attribute("phone");
        assertFalse(nameAttribute.equals(phoneAttribute));
    }
}
