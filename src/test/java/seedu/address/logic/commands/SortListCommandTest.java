package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.sort.AddressComparator;
import seedu.address.model.sort.ContactTypeComparator;
import seedu.address.model.sort.EmailComparator;
import seedu.address.model.sort.NameComparator;
import seedu.address.model.sort.PhoneComparator;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortListCommand.
 */
public class SortListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortNameAsc_showsSortedNameList() {
        expectedModel.updateFilteredPersonList(new NameComparator());
        assertCommandSuccess(
                new SortListCommand(new NameComparator(), true),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortNameDesc_showsSortedNameList() {
        expectedModel.updateFilteredPersonList(new NameComparator().reversed());
        assertCommandSuccess(
                new SortListCommand(new NameComparator(), false),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortPhoneAsc_showsSortedPhoneList() {
        expectedModel.updateFilteredPersonList(new PhoneComparator());
        assertCommandSuccess(
                new SortListCommand(new PhoneComparator(), true),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortPhoneDesc_showsSortedPhoneList() {
        expectedModel.updateFilteredPersonList(new PhoneComparator().reversed());
        assertCommandSuccess(
                new SortListCommand(new PhoneComparator(), false),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortEmailAsc_showsSortedEmailList() {
        expectedModel.updateFilteredPersonList(new EmailComparator());
        assertCommandSuccess(
                new SortListCommand(new EmailComparator(), true),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortEmailDesc_showsSortedEmailList() {
        expectedModel.updateFilteredPersonList(new EmailComparator().reversed());
        assertCommandSuccess(
                new SortListCommand(new EmailComparator(), false),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortAddressAsc_showsSortedAddressList() {
        expectedModel.updateFilteredPersonList(new AddressComparator());
        assertCommandSuccess(
                new SortListCommand(new AddressComparator(), true),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortAddressDesc_showsSortedAddressList() {
        expectedModel.updateFilteredPersonList(new AddressComparator().reversed());
        assertCommandSuccess(
                new SortListCommand(new AddressComparator(), false),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortContactTypeAsc_showsSortedContactTypeList() {
        expectedModel.updateFilteredPersonList(new ContactTypeComparator());
        assertCommandSuccess(
                new SortListCommand(new ContactTypeComparator(), true),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }

    @Test
    public void execute_sortContactTypeDesc_showsSortedContactTypeList() {
        expectedModel.updateFilteredPersonList(new ContactTypeComparator().reversed());
        assertCommandSuccess(
                new SortListCommand(new ContactTypeComparator(), false),
                model,
                SortListCommand.MESSAGE_SUCCESS,
                expectedModel
        );
    }
}
