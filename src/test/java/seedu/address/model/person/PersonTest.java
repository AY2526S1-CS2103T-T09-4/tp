package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CARL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CARL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.customer.Customer;
import seedu.address.testutil.CustomerBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Customer person = new CustomerBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(AMY.isSamePerson(AMY));

        // null -> returns false
        assertFalse(AMY.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Customer editedAmy = new CustomerBuilder(AMY).withPhone(VALID_PHONE_CARL).withEmail(VALID_EMAIL_CARL)
                .withAddress(VALID_ADDRESS_CARL).withTags(VALID_TAG_CARL).build();
        assertTrue(AMY.isSamePerson(editedAmy));


        // name differs in case, all other attributes same -> returns true
        editedAmy = new CustomerBuilder(AMY).withName(VALID_NAME_AMY.toLowerCase()).build();
        assertTrue(AMY.isSamePerson(editedAmy));

        // different name, all other attributes same -> returns false
        editedAmy = new CustomerBuilder(AMY).withName(VALID_NAME_CARL).build();
        assertFalse(AMY.isSamePerson(editedAmy));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_AMY + " ";
        editedAmy = new CustomerBuilder(AMY).withName(nameWithTrailingSpaces).build();
        assertFalse(AMY.isSamePerson(editedAmy));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person amyCopy = new CustomerBuilder(AMY).build();
        assertTrue(AMY.equals(amyCopy));

        // same object -> returns true
        assertTrue(AMY.equals(AMY));

        // null -> returns false
        assertFalse(AMY.equals(null));

        // different type -> returns false
        assertFalse(AMY.equals(5));

        // different person -> returns false
        assertFalse(AMY.equals(CARL));

        // different name -> returns false
        Customer editedAmy = new CustomerBuilder(AMY).withName(VALID_NAME_CARL).build();
        assertFalse(AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new CustomerBuilder(AMY).withPhone(VALID_PHONE_CARL).build();
        assertFalse(AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new CustomerBuilder(AMY).withEmail(VALID_EMAIL_CARL).build();
        assertFalse(AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new CustomerBuilder(AMY).withAddress(VALID_ADDRESS_CARL).build();
        assertFalse(AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new CustomerBuilder(AMY).withTags(VALID_TAG_CARL).build();
        assertFalse(AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        String expected = Customer.class.getCanonicalName() + "{name=" + AMY.getName() + ", phone=" + AMY.getPhone()
                + ", email=" + AMY.getEmail() + ", address=" + AMY.getAddress() + ", tags=" + AMY.getTags()
                + ", note=" + AMY.getNote() + "}";
        assertEquals(expected, AMY.toString());
    }
}
