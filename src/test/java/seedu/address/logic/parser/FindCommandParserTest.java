package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AllOfPersonPredicates;
import seedu.address.model.person.NameContainsKeywordsPredicate;
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
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new AllOfPersonPredicates(List.of(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"))
                ))
        );

        // no leading whitespaces
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
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
    public void parse_namePreamble_returnsFindCommand() {
        // no prefix
        FindCommand expected = new FindCommand(
                new AllOfPersonPredicates(List.of(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"))
                ))
        );
        assertParseSuccess(parser, "Alice Bob", expected);

        // spacing-insensitive
        assertParseSuccess(parser, " \n  Alice  \t Bob   ", expected);

        // case-insensitive tokens
        FindCommand expectedCase = new FindCommand(
                new AllOfPersonPredicates(List.of(
                        new NameContainsKeywordsPredicate(Arrays.asList("aLiCe", "bOb"))
                ))
        );
        assertParseSuccess(parser, "aLiCe bOb", expectedCase);
    }

    @Test
    public void execute_nameAndAddress_singlePersonFound() throws Exception {
        FindCommandParser parser = new FindCommandParser();
        FindCommand command = parser.parse("find n/Benson a/Clementi");

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expected.updateFilteredPersonList(p ->
                p.getName().fullName.matches("(?i).*\\bBenson\\b.*")
                        && p.getAddress().value.toLowerCase().contains("clementi"));

        assertCommandSuccess(command, actual, expectedMessage, expected);
        assertEquals(java.util.List.of(seedu.address.testutil.TypicalPersons.BENSON),
                actual.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndEmail_singlePersonFound() throws Exception {
        FindCommandParser parser = new FindCommandParser();
        FindCommand command = parser.parse("n/Benson e/johnd@example.com");

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expected.updateFilteredPersonList(p ->
                p.getName().fullName.matches("(?i).*\\bBenson\\b.*")
                        && p.getEmail().value.toLowerCase().contains("johnd@example.com"));

        assertCommandSuccess(command, actual, expectedMessage, expected);
        assertEquals(java.util.List.of(seedu.address.testutil.TypicalPersons.BENSON),
                actual.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndPhone_singlePersonFound() throws Exception {
        FindCommandParser parser = new FindCommandParser();
        FindCommand command = parser.parse("n/Benson p/98765432");

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expected.updateFilteredPersonList(p ->
                p.getName().fullName.matches("(?i).*\\bBenson\\b.*")
                        && p.getPhone().value.contains("98765432"));

        assertCommandSuccess(command, actual, expectedMessage, expected);
        assertEquals(java.util.List.of(seedu.address.testutil.TypicalPersons.BENSON),
                actual.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndTag_singlePersonFound() throws Exception {
        FindCommandParser parser = new FindCommandParser();
        FindCommand command = parser.parse("n/Benson t/frequentCustomer");

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expected.updateFilteredPersonList(p ->
                p.getName().fullName.matches("(?i).*\\bBenson\\b.*")
                        && p.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase("frequentCustomer")));

        assertCommandSuccess(command, actual, expectedMessage, expected);
        assertEquals(java.util.List.of(seedu.address.testutil.TypicalPersons.BENSON),
                actual.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndShifts_singleStaffFound() throws Exception {
        var target = TypicalPersons.CARL;
        var targetShift = target.getShifts().stream()
                .findFirst()
                .map(Object::toString)
                .orElseThrow(() -> new AssertionError("Target must have at least one shift"));

        FindCommand command = parser.parse("find n/Carl shifts/ " + targetShift);

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expected.updateFilteredPersonList(p ->
                p.getName().fullName.matches("(?i).*\\bCarl\\b.*")
                        && p.getContactType() == seedu.address.model.person.Person.ContactType.STAFF
                        && p.getShifts().stream().map(Object::toString).anyMatch(s -> s.equals(targetShift))
        );

        assertCommandSuccess(command, actual, expectedMessage, expected);
        assertEquals(java.util.List.of(target), actual.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndItems_singleSupplierFound() throws Exception {
        FindCommand command = parser.parse("find n/Elle items/egg");

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expected.updateFilteredPersonList(p ->
                p.getName().fullName.matches("(?i).*\\bElle\\b.*")
                        && p.getContactType() == seedu.address.model.person.Person.ContactType.SUPPLIER
                        && p.getItems().stream()
                        .map(Object::toString)
                        .map(String::toLowerCase)
                        .anyMatch(s -> s.contains("egg"))
        );

        assertCommandSuccess(command, actual, expectedMessage, expected);
        assertEquals(java.util.List.of(seedu.address.testutil.TypicalPersons.ELLE),
                actual.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndDays_singleSupplierFound() throws Exception {
        FindCommand command = parser.parse("find n/Elle days/2030-10-10");

        Model actual = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expected = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        expected.updateFilteredPersonList(p ->
                p.getName().fullName.matches("(?i).*\\bElle\\b.*")
                        && p.getContactType() == seedu.address.model.person.Person.ContactType.SUPPLIER
                        && p.getDays().stream()
                        .map(Object::toString)
                        .anyMatch(s -> s.contains("2030-10-10"))
        );

        assertCommandSuccess(command, actual, expectedMessage, expected);
        assertEquals(java.util.List.of(seedu.address.testutil.TypicalPersons.ELLE),
                actual.getFilteredPersonList());
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
    public void execute_days_returnsZero() throws Exception {
        // Non-supplier person with days
        FindCommand cmd = parser.parse("find n/Alice days/ 2025-10-20");
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

