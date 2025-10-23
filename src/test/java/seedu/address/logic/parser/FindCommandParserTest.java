package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void execute_nameAndAddress_singlePersonFound() throws Exception {
        FindCommandParser parser = new FindCommandParser();
        FindCommand command = parser.parse("n/Benson a/Clementi");

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
}
