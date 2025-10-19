package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortListCommand;
import seedu.address.model.sort.NameComparator;

public class SortListCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortListCommand.MESSAGE_USAGE);

    private SortListCommandParser parser = new SortListCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no attribute specified
        assertParseFailure(parser, "o/asc", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_orderSpecified_success() {
        String userInput = "name o/asc";

        SortListCommand expectedCommand = new SortListCommand(new NameComparator(), true);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = "name o/desc";

        expectedCommand = new SortListCommand(new NameComparator(), false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_orderNotSpecified_success() {
        String userInput = "name";

        SortListCommand expectedCommand = new SortListCommand(new NameComparator(), true);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
