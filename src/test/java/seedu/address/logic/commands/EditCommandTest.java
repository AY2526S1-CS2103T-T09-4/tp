package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FIELD_ENTERED;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Supplier;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.StaffBuilder;
import seedu.address.testutil.SupplierBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_customerSomeFieldsSpecifiedUnfilteredList_success() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Customer lastPerson = (Customer) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        CustomerBuilder personInList = new CustomerBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withTags(VALID_TAG_AMY).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_AMY).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_customerWrongFieldShifts_failure() throws ParseException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Customer lastPerson = (Customer) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        List<String> shiftsList = new ArrayList<>();
        shiftsList.add("12/12/2025");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withShift(shiftsList).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_SHIFTS));
    }

    @Test
    public void execute_customerWrongFieldItems_failure() throws ParseException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Customer lastPerson = (Customer) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        List<String> itemList = new ArrayList<>();
        itemList.add("Eggs");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withItems(itemList).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_ITEMS));
    }

    @Test
    public void execute_customerWrongFieldDays_failure() throws ParseException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_CUSTOMERS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Customer lastPerson = (Customer) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        List<String> daysList = new ArrayList<>();
        daysList.add("2025-12-12");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withDays(daysList).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_DAYS));
    }

    @Test
    public void execute_staffSomeFieldsSpecifiedUnfilteredList_success() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STAFFS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Staff lastPerson = (Staff) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        StaffBuilder personInList = new StaffBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withTags(VALID_TAG_AMY).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_AMY).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_staffWrongFieldsPoints_failure() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STAFFS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Staff lastPerson = (Staff) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPoints("3").build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_POINTS));
    }

    @Test
    public void execute_staffWrongFieldItems_failure() throws ParseException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STAFFS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Staff lastPerson = (Staff) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        List<String> itemList = new ArrayList<>();
        itemList.add("Eggs");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withItems(itemList).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_ITEMS));
    }

    @Test
    public void execute_staffWrongFieldDays_failure() throws ParseException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_STAFFS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Staff lastPerson = (Staff) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        List<String> daysList = new ArrayList<>();
        daysList.add("2025-12-12");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withDays(daysList).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_DAYS));
    }

    @Test
    public void execute_supplierSomeFieldsSpecifiedUnfilteredList_success() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Supplier lastPerson = (Supplier) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        SupplierBuilder personInList = new SupplierBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withTags(VALID_TAG_AMY).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withTags(VALID_TAG_AMY).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_supplierWrongFields_failure() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Supplier lastPerson = (Supplier) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPoints("3").build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_POINTS));
    }

    @Test
    public void execute_supplierWrongFieldShifts_failure() throws ParseException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Supplier lastPerson = (Supplier) model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        List<String> shiftsList = new ArrayList<>();
        shiftsList.add("12/12/2025");
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withShift(shiftsList).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_INVALID_FIELD_ENTERED, PREFIX_SHIFTS));
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
