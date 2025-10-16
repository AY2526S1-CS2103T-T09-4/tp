package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.supplier.Supplier;

/**
 * Adds a Supplier to the address book.
 * Command word mirrors the existing pattern ("add role …").
 * Example:
 *   add supplier n/Ah Hock p/98765432 e/ahhock@example.com a/123 Clementi Ave 3 t/preferred
 *               it/Flour,Eggs dy/MON,THU no/halal supplier
 */
public class AddSupplierCommand extends AddCommand {

    public static final String COMMAND_WORD = AddCommand.COMMAND_WORD + " supplier";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a supplier to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ITEMS + "ITEM[,ITEM,...] "
            + PREFIX_DAYS + "DAY[,DAY,...] "
            + "[" + PREFIX_NOTE + "NOTES]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "ahhock@example.com "
            + PREFIX_ADDRESS + "123 Clementi Ave 3 "
            + PREFIX_ITEMS + "Flour, Eggs "
            + PREFIX_DAYS + "2025-10-10 "
            + PREFIX_NOTE + "Halal supplier "
            + PREFIX_TAG + "Coffee bean supplier";

    /**
     * Creates an {@code AddSupplierCommand} to add the specified {@link Supplier}.
     *
     * @param supplier the supplier to be added. Must not be {@code null}.
     * @throws NullPointerException if {@code supplier} is null
     */
    public AddSupplierCommand(Supplier supplier) {
        super(supplier);
    }
}
