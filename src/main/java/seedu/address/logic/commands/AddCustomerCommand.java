package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.person.customer.Customer;

/**
 * Adds a {@link Customer} to the address book.
 * This command is invoked using the keyword {@code add_customer}, followed by the required
 * customer details such as name, phone, email, and address. Optional tags may also be provided.
 */
public class AddCustomerCommand extends AddCommand {

    public static final String COMMAND_WORD = AddCommand.COMMAND_WORD + " customer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a customer to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NOTE + "NOTES "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NOTE + "allergic to nuts "
            + PREFIX_TAG + "frequent";

    /**
     * Creates an AddCustomerCommand to add the specified {@code Customer}
     */
    public AddCustomerCommand(Customer customer) {
        super(customer);
    }
}
