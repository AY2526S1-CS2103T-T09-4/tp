package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.staff.Staff;

/**
 * Adds a Staff to the address book.
 * Command word mirrors the existing pattern ("add role …").
 * Example:
 *   add staff n/Ah Hock p/98765432 e:ahhock@example.com a/123 Clementi Ave 3 t/preferred
 *   shifts/2025-12-03, 2025-12-04, 2025-12-05 no/can only do weekdays
 *
 */
public class AddStaffCommand extends AddCommand {

    public static final String COMMAND_WORD = AddCommand.COMMAND_WORD + " staff";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a staff to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_SHIFTS + "SHIFT[, SHIFT,...] "
            + "[" + PREFIX_NOTE + "NOTES]\n"
            + "[" + PREFIX_TAG + "TAG]... "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Ah Hock "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "ahhock@example.com "
            + PREFIX_ADDRESS + "123 Clementi Ave 3 "
            + PREFIX_SHIFTS + "2025-12-04 "
            + PREFIX_NOTE + "can only do weekdays"
            + PREFIX_TAG + "preferred ";

    /**
     * Initialises AddStaffCommand.
     */
    public AddStaffCommand(Staff staff) {
        super(staff);
    }
}
