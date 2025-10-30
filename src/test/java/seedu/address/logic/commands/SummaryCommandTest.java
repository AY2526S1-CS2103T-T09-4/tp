package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.Person.ContactType.CUSTOMER;
import static seedu.address.model.person.Person.ContactType.STAFF;
import static seedu.address.model.person.Person.ContactType.SUPPLIER;
import static seedu.address.testutil.TypicalPersons.getSkewedAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SummaryCommand.
 */
public class SummaryCommandTest {
    private Model model;
    private Model expectedModel;
    private final Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
    private final SummaryCommand summary = new SummaryCommand();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noPersons_emptySummary() {
        CommandResult result = summary.execute(emptyModel);

        String expectedMessage = "Important dates summarised:";
        assertTrue(result.getFeedbackToUser().startsWith(expectedMessage));
        assertFalse(result.getFeedbackToUser().contains("Staff"));
        assertFalse(result.getFeedbackToUser().contains("Supplier"));
    }

    @Test
    public void execute_withStaffAndSupplier_success() {
        CommandResult result = summary.execute(model);

        String expected = "Important dates summarised:\n\n"
                + "Staff's shift:\n"
                + "Carl Kurz: 10/10/2030\n"
                + "Daniel Meier: 10/10/2030\n\n"
                + "Supplier's day:\n"
                + "Elle Meyer (egg): 2030-10-10\n"
                + "Fiona Kunz (egg): 2030-10-10";

        assertEquals(expected.trim(), result.getFeedbackToUser());
    }

    @Test
    public void execute_withOnlyCustomer_emptySummary() {
        Model skewedModel = new ModelManager(getSkewedAddressBook(CUSTOMER), new UserPrefs());

        CommandResult result = summary.execute(skewedModel);

        String expectedMessage = "Important dates summarised:";
        assertTrue(result.getFeedbackToUser().startsWith(expectedMessage));
        assertFalse(result.getFeedbackToUser().contains("Staff"));
        assertFalse(result.getFeedbackToUser().contains("Supplier"));
    }

    @Test
    public void execute_withOnlyStaff_onlyStaffSummary() {
        Model skewedModel = new ModelManager(getSkewedAddressBook(STAFF), new UserPrefs());

        CommandResult result = summary.execute(skewedModel);

        String expectedMessage = "Important dates summarised:\n\n"
                + "Staff's shift:\n"
                + "Carl Kurz: 10/10/2030\n"
                + "Daniel Meier: 10/10/2030";

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertFalse(result.getFeedbackToUser().contains("Supplier"));
    }

    @Test
    public void execute_withOnlySupplier_onlySupplierSummary() {
        Model skewedModel = new ModelManager(getSkewedAddressBook(SUPPLIER), new UserPrefs());

        CommandResult result = summary.execute(skewedModel);

        String expectedMessage = "Important dates summarised:\n\n"
                + "Supplier's day:\n"
                + "Elle Meyer (egg): 2030-10-10\n"
                + "Fiona Kunz (egg): 2030-10-10";

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertFalse(result.getFeedbackToUser().contains("Staff"));
    }
}
