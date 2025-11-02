package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    private Model freshModel() {
        return new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    private static List<String> tokenize(String s) {
        return Arrays.stream(s.split("[^0-9A-Za-z@._:-]+"))
                .filter(t -> !t.isBlank())
                .toList();
    }

    private AddressBook getTypicalAddressBook() {
        return TypicalPersons.getTypicalAddressBook();
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws Exception {
        FindCommand command1 = parser.parse("Alice");

        Model actual1 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected1 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected1.updateFilteredPersonList(p -> p.equals(TypicalPersons.ALICE));

        assertCommandSuccess(command1, actual1,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expected1);

        FindCommand command2 = parser.parse(" \n  Benson Meier \t ");

        Model actual2 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected2 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected2.updateFilteredPersonList(p -> p.equals(TypicalPersons.BENSON));

        assertCommandSuccess(command2, actual2,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expected2);
    }

    @Test
    public void parse_emptyValue_throwsException() {
        String msg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

        // name, phone, email, address
        assertParseFailure(parser, "find n/ p/123", msg);
        assertParseFailure(parser, "find p/ n/Alice", msg);
        assertParseFailure(parser, "find e/ n/Alice", msg);
        assertParseFailure(parser, "find a/ n/Alice", msg);

        // tag, items, days, shifts
        assertParseFailure(parser, "find t/ n/Alice", msg);
        assertParseFailure(parser, "find items/ n/Elle", msg);
        assertParseFailure(parser, "find days/ n/Elle", msg);
        assertParseFailure(parser, "find shifts/ n/Carl", msg);
    }


    @Test
    public void execute_noMatch_zeroPersonsListed() throws Exception {
        FindCommand cmd = parser.parse("find n/rooney e/rooney@example.zzz");

        Model actual = freshModel();
        Model expected = freshModel();

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        expected.updateFilteredPersonList(p -> false);

        assertCommandSuccess(cmd, actual, expectedMessage, expected);
        assertEquals(List.of(), actual.getFilteredPersonList());
    }

    @Test
    public void parse_namePreamble_returnsFindCommand() throws Exception {
        // no prefix
        FindCommand cmd1 = parser.parse("Alice");

        Model actual1 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected1 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected1.updateFilteredPersonList(p -> p.equals(TypicalPersons.ALICE));

        assertCommandSuccess(cmd1, actual1,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expected1);

        // spacing-insensitive
        FindCommand cmd2 = parser.parse(" \n  Benson  \t Meier   ");

        Model actual2 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected2 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected2.updateFilteredPersonList(p -> p.equals(TypicalPersons.BENSON));

        assertCommandSuccess(cmd2, actual2,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expected2);

        // case-insensitive
        FindCommand cmd3 = parser.parse("bEnSoN mEiEr");

        Model actual3 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected3 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected3.updateFilteredPersonList(p -> p.equals(TypicalPersons.BENSON));

        assertCommandSuccess(cmd3, actual3,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expected3);
    }

    @Test
    public void execute_address_returnsZero() throws Exception {
        FindCommand cmd = parser.parse("find a/123 Clementi");

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected.updateFilteredPersonList(p -> false);

        assertCommandSuccess(cmd, actual,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expected);
        assertEquals(List.of(), actual.getFilteredPersonList());
    }

    @Test
    public void execute_shifts_returnsZero() throws Exception {
        // Non-staff person with shifts
        FindCommand cmd = parser.parse("find n/Alice shifts/ 2030-10-10");
        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected.updateFilteredPersonList(p -> false);

        assertCommandSuccess(cmd, actual,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expected);
        assertEquals(java.util.List.of(), actual.getFilteredPersonList());
    }

    @Test
    public void execute_items_returnsZero() throws Exception {
        // Non-supplier person with items
        FindCommand cmd = parser.parse("find n/Alice items/egg");
        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expected.updateFilteredPersonList(p -> false);

        assertCommandSuccess(cmd, actual,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expected);
        assertEquals(java.util.List.of(), actual.getFilteredPersonList());
    }

    @Test
    public void parse_noItemsMatch_returnsZero() throws Exception {
        Model actual = freshModel();
        Model expected = freshModel();

        FindCommand cmd = parser.parse("items/flour");

        expected.updateFilteredPersonList(p -> false);
        assertCommandSuccess(cmd, actual,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expected);
        assertEquals(List.of(), actual.getFilteredPersonList());
    }

    @Test
    public void parse_oneItemWrong_returnsZero() throws Exception {
        Model actual = freshModel();
        Model expected = freshModel();

        FindCommand cmd = parser.parse("items/egg, flour");

        expected.updateFilteredPersonList(p -> false);

        assertCommandSuccess(cmd, actual,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expected);
        assertEquals(java.util.List.of(), actual.getFilteredPersonList());
    }
    @Test

    public void parse_items_supplierHayBuilt_butNeedleMissing_returnsZero() throws Exception {
        Model actual = freshModel();
        Model expected = freshModel();

        FindCommand cmd = parser.parse("items/eggs");

        expected.updateFilteredPersonList(p -> false);

        assertCommandSuccess(cmd, actual,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expected);
        assertEquals(java.util.List.of(), actual.getFilteredPersonList());
    }

    @Test
    public void execute_days_returnsZero() throws Exception {
        // Non-supplier person with days
        FindCommand cmd = parser.parse("find n/Alice days/2025-10-20");
        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        expected.updateFilteredPersonList(p -> false);

        assertCommandSuccess(cmd, actual,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0), expected);
        assertEquals(java.util.List.of(), actual.getFilteredPersonList());
    }

    @Test
    public void parse_unknownPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("x/abc"));
    }

    @Test
    public void parse_unknownPrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("n/Alice x/abc e/alice@example.com"));
    }

    @Test
    public void parse_upperCasePrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("N/Alice"));
    }
}

