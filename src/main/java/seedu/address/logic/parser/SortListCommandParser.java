package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.model.sort.Attribute.isValidAttribute;

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
        String[] words = args.trim().split(" ", 2);
        String preamble = words[0].trim();

        if (!isValidAttribute(preamble)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortListCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_ORDER);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT_ORDER);
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

        Comparator<Person> comparator = ParserUtil.parseComparator(preamble);

        return new SortListCommand(comparator, isAsc);
    }
}
