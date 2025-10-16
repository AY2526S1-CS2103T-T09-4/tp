package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.supplier.Items;
import seedu.address.model.person.supplier.Days;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_NOTE = "x".repeat(201);
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_SHIFTS = "10/1/2025";
    private static final String INVALID_DAYS = "10/1/2025";
    private static final String INVALID_ITEMS = "@@@";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_NOTE = BENSON.getNote().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedShift> EMPTY_SHIFTS = new ArrayList<>();
    private static final List<JsonAdaptedShift> VALID_SHIFTS = new ArrayList<>(List.of(
            new JsonAdaptedShift(new Shift(LocalDate.parse("2025-10-10")))));
    private static final List<JsonAdaptedDays> EMPTY_DAYS = new ArrayList<>();
    private static final List<JsonAdaptedDays> VALID_DAYS = new ArrayList<>(List.of(
            new JsonAdaptedDays(new Days(LocalDate.parse("2025-10-10")))));
    private static final List<JsonAdaptedItems> EMPTY_ITEMS = new ArrayList<>();
    private static final List<JsonAdaptedItems> VALID_ITEMS = new ArrayList<>(List.of(
            new JsonAdaptedItems("Flour"), new JsonAdaptedItems("Eggs")));

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(Person.ContactType.CUSTOMER, INVALID_NAME,
                        VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.CUSTOMER, null,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                        INVALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                        VALID_PHONE, INVALID_EMAIL,
                        VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                        VALID_PHONE, VALID_EMAIL,
                        INVALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                null, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                        VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, invalidTags, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                        VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, INVALID_NOTE);
        String expectedMessage = Note.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.CUSTOMER, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_validShift_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.STAFF, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_SHIFTS, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);

        Person modelPerson = person.toModelType();

        assertEquals(VALID_SHIFTS.size(), modelPerson.getShifts().size());
        assertEquals("2025-10-10", modelPerson.getShifts().get(0).toString());
    }

    @Test
    public void toModelType_invalidShift_throwsIllegalValueException() {
        List<JsonAdaptedShift> invalidShifts = new ArrayList<>();
        invalidShifts.add(new JsonAdaptedShift(INVALID_SHIFTS));

        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.STAFF, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, invalidShifts, EMPTY_ITEMS, EMPTY_DAYS, VALID_NOTE);

        String expectedMessage = Shift.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_validItemsDays_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.SUPPLIER, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, VALID_ITEMS, VALID_DAYS, VALID_NOTE);

        Person modelPerson = person.toModelType();

        assertEquals(VALID_DAYS.size(), modelPerson.getDays().size());
        assertEquals("2025-10-10", modelPerson.getDays().get(0).toString());
        assertEquals(List.of("Flour", "Eggs"), modelPerson.getItems().stream().map(Object::toString).toList());
    }

    @Test
    public void toModelType_invalidItems_throwsIllegalValueException() {
        List<JsonAdaptedItems> invalidItems = new ArrayList<>();
        invalidItems.add(new JsonAdaptedItems(INVALID_ITEMS));


        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.STAFF, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, invalidItems, VALID_DAYS, VALID_NOTE);

        String expectedMessage = Items.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDays_throwsIllegalValueException() {
        List<JsonAdaptedDays> invalidDays = new ArrayList<>();
        invalidDays.add(new JsonAdaptedDays(INVALID_DAYS));


        JsonAdaptedPerson person = new JsonAdaptedPerson(Person.ContactType.STAFF, VALID_NAME,
                VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, EMPTY_SHIFTS, VALID_ITEMS, invalidDays, VALID_NOTE);

        String expectedMessage = Days.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}