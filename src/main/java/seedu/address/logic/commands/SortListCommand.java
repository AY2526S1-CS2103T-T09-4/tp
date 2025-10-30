package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts persons in displayed person list by the specified attribute.
 */
public class SortListCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Displaying sorted list.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list of persons by a specified attribute and order.\n\n"
            + "**Format:**\n"
            + COMMAND_WORD + " ATTRIBUTE "
            + "[" + PREFIX_SORT_ORDER + "ORDER]\n\n"
            + "**Accepted Values:**\n"
            + "    ATTRIBUTE: 'name', 'phone', 'email', 'address', or 'type'.\n"
            + "    ORDER: 'asc' (Ascending, default) or 'desc' (Descending).\n\n"
            + "**Examples:**\n"
            + COMMAND_WORD + " name\n"
            + COMMAND_WORD + " email " + PREFIX_SORT_ORDER + "desc";


    private final Comparator<Person> comparator;
    private final boolean isAsc;

    /**
     * Creates a SortListCommand to sort current displayed list.
     *
     * @param comparator the comparator of persons to list
     */
    public SortListCommand(Comparator<Person> comparator, boolean isAsc) {
        requireNonNull(comparator);
        this.comparator = comparator;
        this.isAsc = isAsc;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(isAsc ? comparator : comparator.reversed());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortListCommand)) {
            return false;
        }

        SortListCommand otherSortListCommand = (SortListCommand) other;
        return comparator.equals(otherSortListCommand.comparator) && isAsc == otherSortListCommand.isAsc;
    }
}
