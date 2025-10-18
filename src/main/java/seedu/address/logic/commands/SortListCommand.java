package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.Comparator;

import static java.util.Objects.requireNonNull;

public class SortListCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort by/<name | type> o/<asc | dsc>";
    public static final String MESSAGE_SUCCESS_FORMAT = "Displaying sorted list.";

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
        return new CommandResult(MESSAGE_SUCCESS_FORMAT);
    }
}
