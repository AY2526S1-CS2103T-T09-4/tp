package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Supplier;

/**
 * Summarises all important dates in the address book.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Important dates summarised:\n\n";



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        StringBuilder staffSummary = new StringBuilder();
        StringBuilder supplierSummary = new StringBuilder();
        ObservableList<Person> list = model.getFilteredPersonList();
        for (Person person : list) {
            switch(person.getDisplayType()) {
            case STAFF:
                staffSummary.append(formatStaff(person)).append("\n");
                break;
            case SUPPLIER:
                supplierSummary.append(formatSupplier(person)).append("\n");
                break;
            default: // Do nothing
            }
        }

        StringBuilder result = new StringBuilder(MESSAGE_SUCCESS);

        if (!staffSummary.isEmpty()) {
            result.append("Staff's shift:\n").append(staffSummary).append("\n");
        }

        if (!supplierSummary.isEmpty()) {
            result.append("Supplier's day:\n").append(supplierSummary).append("\n");
        }

        return new CommandResult(result.toString().trim());
    }

    private String formatStaff(Person p) {
        assert p instanceof Staff;
        Staff staff = (Staff) p;

        return String.format("%s: %s", staff.getName(), staff.getShifts());
    }

    private String formatSupplier(Person p) {
        assert p instanceof Supplier;
        Supplier supplier = (Supplier) p;

        return String.format("%s: %s", supplier.getName(), supplier.getDays());
    }
}
