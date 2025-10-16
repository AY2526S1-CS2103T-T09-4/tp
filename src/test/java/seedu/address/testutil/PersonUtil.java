package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.AddStaffCommand;
import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add customer command string for adding the {@code person}.
     */
    public static String getAddCustomerCommand(Customer customer) {
        return AddCustomerCommand.COMMAND_WORD + " " + getCustomerDetails(customer);
    }

    /**
     * Returns an add staff command string for adding the {@code person}.
     */
    public static String getAddStaffCommand(Staff staff) {
        return AddStaffCommand.COMMAND_WORD + " " + getStaffDetails(staff);
    }

    /**
     * Returns an add supplier command string for adding the {@code person}.
     */
    public static String getAddSupplierCommand(Supplier supplier) {
        return AddSupplierCommand.COMMAND_WORD + " " + getSupplierDetails(supplier);
    }

    /**
     * Returns the part of command string for the given {@code customer}'s details.
     */
    public static String getCustomerDetails(Customer customer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + customer.getName().fullName + " ");
        sb.append(PREFIX_PHONE + customer.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + customer.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + customer.getAddress().value + " ");
        customer.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code supplier}'s details.
     */
    public static String getSupplierDetails(Supplier supplier) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(supplier.getName().fullName).append(" ");
        sb.append(PREFIX_PHONE).append(supplier.getPhone().value).append(" ");
        sb.append(PREFIX_EMAIL).append(supplier.getEmail().value).append(" ");
        sb.append(PREFIX_ADDRESS).append(supplier.getAddress().value).append(" ");

        sb.append("days/").append(
                supplier.getDays().stream()
                        .map(Object::toString) // or a proper formatter
                        .collect(Collectors.joining(", "))
        ).append(" ");

        sb.append("items/").append(
                supplier.getItems().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "))
        ).append(" ");

        supplier.getTags().forEach(tag -> sb.append(PREFIX_TAG).append(tag.tagName).append(" "));

        return sb.toString();
    }


    /**
     * Returns the part of command string for the given {@code staff}'s details.
     */
    public static String getStaffDetails(Staff staff) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(staff.getName().fullName).append(" ");
        sb.append(PREFIX_PHONE).append(staff.getPhone().value).append(" ");
        sb.append(PREFIX_EMAIL).append(staff.getEmail().value).append(" ");
        sb.append(PREFIX_ADDRESS).append(staff.getAddress().value).append(" ");

        sb.append("shifts/").append(
                staff.getShifts().stream()
                        .map(Shift::toString)
                        .collect(Collectors.joining(", "))
        ).append(" ");

        staff.getTags().forEach(tag -> sb.append(PREFIX_TAG).append(tag.tagName).append(" "));

        return sb.toString();
    }


    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
