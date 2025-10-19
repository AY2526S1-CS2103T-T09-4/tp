package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_TYPE;

import java.util.Comparator;

import seedu.address.logic.commands.SortListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new SortListCommand object
 */
public class SortListCommandParser implements Parser<SortListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortListCommand
     * and returns a SortListCommand object for execution.
     * @throws ParseException if the user input does not match any comparator
     */
    public SortListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_TYPE, PREFIX_SORT_ORDER);

        if (argMultimap.getValue(PREFIX_SORT_TYPE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortListCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT_TYPE, PREFIX_SORT_ORDER);
        boolean isAsc;
        if (argMultimap.getValue(PREFIX_SORT_ORDER).isEmpty()) {
            isAsc = true;
        } else {
            String sortOrder = argMultimap.getValue(PREFIX_SORT_ORDER).get().trim().toLowerCase();

            if (sortOrder.equals("asc")) {
                isAsc = true;
            } else if (sortOrder.equals("desc")) {
                isAsc = false;
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortListCommand.MESSAGE_USAGE));
            }
        }

        Comparator<Person> comparator = ParserUtil.parseComparator(argMultimap.getValue(PREFIX_SORT_TYPE).get());

        return new SortListCommand(comparator, isAsc);
    }
}
