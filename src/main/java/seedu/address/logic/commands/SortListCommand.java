package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_TYPE;

public class SortListCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Displaying sorted list.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of persons by a specified attribute and order.\n"
            + "Parameters: "
            + PREFIX_SORT_TYPE + "<ATTRIBUTE> "
            + "[" + PREFIX_SORT_ORDER + "<ORDER>" + "]\n"
            + "Accepted values:\n"
            + "  <ATTRIBUTE>: 'name', 'phone', 'email', 'address', or 'type'.\n"
            + "  <ORDER>: 'asc' (Ascending, default) or 'desc' (Descending).\n"
            + "Example 1 (Name, Ascending): " + COMMAND_WORD + " by/name\n"
            + "Example 2 (Email, Descending): " + COMMAND_WORD + " by/email o/desc";


    private final Comparator<Person> comparator;

    /**
     * Creates a SortListCommand to sort current displayed list.
     *
     * @param comparator the comparator of persons to list
     */
    public SortListCommand(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
