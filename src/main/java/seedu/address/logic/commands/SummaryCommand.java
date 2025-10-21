package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Summarises all important dates in the address book.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS_FORMAT = "Important dates summarised:\n";



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder result = new StringBuilder();
        ObservableList<Person> list = model.getFilteredPersonList();
        for (Person person : list) {
            switch(person.getDisplayType()) {
            case STAFF:
            case SUPPLIER:
                result.append(person);
                break;
            default: // Do nothing
            }
        }
        return new CommandResult(MESSAGE_SUCCESS_FORMAT + result);
    }
}
