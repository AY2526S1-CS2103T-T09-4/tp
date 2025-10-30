package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_AMY_CASE = "amy bee";
    public static final String VALID_NAME_AMY_SPACE = "Amy   Bee";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_TAG_AMY = "frequentCustomer";
    public static final String VALID_TAG_AMY_2 = "prefersDelivery";
    public static final String VALID_NOTE_AMY = "allergic to nuts";
    public static final String VALID_POINTS_AMY = "3";
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String TAG_DESC_AMY = " " + PREFIX_TAG + VALID_TAG_AMY;
    public static final String TAG_DESC_AMY_2 = " " + PREFIX_TAG + VALID_TAG_AMY_2;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String POINTS_DESC_AMY = " " + PREFIX_POINTS + VALID_POINTS_AMY;

    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_PHONE_ALICE = "94351253";
    public static final String VALID_EMAIL_ALICE = "alice@example.com";
    public static final String VALID_ADDRESS_ALICE = "123, Jurong West Ave 6, #08-111";
    public static final String NAME_DESC_ALICE = " " + PREFIX_NAME + VALID_NAME_ALICE;
    public static final String PHONE_DESC_ALICE = " " + PREFIX_PHONE + VALID_PHONE_ALICE;
    public static final String EMAIL_DESC_ALICE = " " + PREFIX_EMAIL + VALID_EMAIL_ALICE;
    public static final String ADDRESS_DESC_ALICE = " " + PREFIX_ADDRESS + VALID_ADDRESS_ALICE;

    public static final String INVALID_NAME_SYMBOL_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NAME_LENGTH_DESC = " " + PREFIX_NAME + "x".repeat(38);
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "frequentCustomer*"; // '*' not allowed in tags
    public static final String INVALID_SHIFTS_FORMAT_DESC = " " + PREFIX_SHIFTS + "2025-10-10"; // invalid date format
    public static final String INVALID_SHIFTS_PAST_DESC = " " + PREFIX_SHIFTS
            + "10/10/2024"; // invalid date as it is in the past
    public static final String INVALID_SHIFTS_DUPLICATE_DESC = " " + PREFIX_SHIFTS
            + "10/10/2030, 10/10/2030"; // invalid dates due to duplicate
    public static final String INVALID_DAYS_DESC = " " + PREFIX_DAYS + "10/11/2025"; // invalid date format
    public static final String INVALID_ITEMS_DESC = " " + PREFIX_ITEMS + "?milk";
    public static final String INVALID_POINTS_DESC = " " + PREFIX_POINTS + "-1";
    public static final String INVALID_NOTES_DESC = " " + PREFIX_NOTE + "x".repeat(201);

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_NAME_CARL = "Carl Kurz";
    public static final String VALID_PHONE_CARL = "95352563";
    public static final String VALID_EMAIL_CARL = "heinz@example.com";
    public static final String VALID_ADDRESS_CARL = "wall street";
    public static final String VALID_TAG_CARL = "bestStaff";
    public static final String VALID_TAG_CARL_2 = "morning";
    public static final String VALID_NOTE_CARL = "allergic to work";
    public static final String VALID_SHIFTS_CARL = "10/10/2030";
    public static final String NAME_DESC_CARL = " " + PREFIX_NAME + VALID_NAME_CARL;
    public static final String PHONE_DESC_CARL = " " + PREFIX_PHONE + VALID_PHONE_CARL;
    public static final String EMAIL_DESC_CARL = " " + PREFIX_EMAIL + VALID_EMAIL_CARL;
    public static final String ADDRESS_DESC_CARL = " " + PREFIX_ADDRESS + VALID_ADDRESS_CARL;
    public static final String TAG_DESC_CARL = " " + PREFIX_TAG + VALID_TAG_CARL;
    public static final String TAG_DESC_CARL_2 = " " + PREFIX_TAG + VALID_TAG_CARL_2;
    public static final String NOTE_DESC_CARL = " " + PREFIX_NOTE + VALID_NOTE_CARL;
    public static final String SHIFTS_DESC_CARL = " " + PREFIX_SHIFTS + VALID_SHIFTS_CARL;


    public static final String VALID_NAME_ELLE = "Elle Meyer";
    public static final String VALID_PHONE_ELLE = "9482224";
    public static final String VALID_EMAIL_ELLE = "werner@example.com";
    public static final String VALID_ADDRESS_ELLE = "michegan ave";
    public static final String VALID_TAG_ELLE = "cheapest";
    public static final String VALID_TAG_ELLE_2 = "fastDelivery";
    public static final String VALID_NOTE_ELLE = "one dollar per gallon";
    public static final String VALID_ITEMS_ELLE = "egg";
    public static final String VALID_DAYS_ELLE = "2030-10-10";
    public static final String NAME_DESC_ELLE = " " + PREFIX_NAME + VALID_NAME_ELLE;
    public static final String PHONE_DESC_ELLE = " " + PREFIX_PHONE + VALID_PHONE_ELLE;
    public static final String EMAIL_DESC_ELLE = " " + PREFIX_EMAIL + VALID_EMAIL_ELLE;
    public static final String ADDRESS_DESC_ELLE = " " + PREFIX_ADDRESS + VALID_ADDRESS_ELLE;
    public static final String TAG_DESC_ELLE = " " + PREFIX_TAG + VALID_TAG_ELLE;
    public static final String TAG_DESC_ELLE_2 = " " + PREFIX_TAG + VALID_TAG_ELLE_2;
    public static final String NOTE_DESC_ELLE = " " + PREFIX_NOTE + VALID_NOTE_ELLE;
    public static final String ITEMS_DESC_ELLE = " " + PREFIX_ITEMS + VALID_ITEMS_ELLE;
    public static final String DAYS_DESC_ELLE = " " + PREFIX_DAYS + VALID_DAYS_ELLE;

    public static final EditCommand.EditPersonDescriptor DESC_AMY;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_AMY, VALID_TAG_AMY_2).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
