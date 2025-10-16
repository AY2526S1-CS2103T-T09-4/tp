package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.CUSTOMER_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.STAFF_TYPE;
import static seedu.address.logic.parser.CliSyntax.SUPPLIER_TYPE;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.logic.commands.AddStaffCommand;
import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Days;
import seedu.address.model.person.supplier.Items;
import seedu.address.model.person.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution. Depending on the command
     * word after "add", it transfers control to the relevant add command parser.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        String[] words = args.trim().split(" ", 2);
        String preamble = words[0].trim();

        if (!isValidType(preamble)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        switch (preamble) {
        case CUSTOMER_TYPE:
            return parseAddCustomerCommand(args);
        case STAFF_TYPE:
            return parseAddStaffCommand(args);
        case SUPPLIER_TYPE:
            return parseAddSupplierCommand(args);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks whether the given command input is one of the three valid types.
     * @param input The command input after "add"
     */
    private boolean isValidType(String input) {
        return input.equals(CUSTOMER_TYPE) || input.equals(STAFF_TYPE) || input.equals(SUPPLIER_TYPE);
    }

    /**
     * Parses the add customer command input according to the given arguments.
     */
    private AddCustomerCommand parseAddCustomerCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_NOTE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).orElse(""));

        Customer customer = new Customer(name, phone, email, address, tagList, note);
        return new AddCustomerCommand(customer);
    }

    private AddStaffCommand parseAddStaffCommand(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_SHIFTS, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_SHIFTS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStaffCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_SHIFTS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        List<Shift> shifts = ParserUtil.parseShifts(argMultimap.getAllValues(PREFIX_SHIFTS));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Staff staff = new Staff(name, phone, email, address, tagList, shifts, note);

        return new AddStaffCommand(staff);
    }

    private AddSupplierCommand parseAddSupplierCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_ITEMS, PREFIX_DAYS, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ITEMS, PREFIX_DAYS)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSupplierCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ITEMS, PREFIX_DAYS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        List<Items> items = ParserUtil.parseItems(argMultimap.getAllValues(PREFIX_ITEMS));
        List<Days> days = ParserUtil.parseDays(argMultimap.getAllValues(PREFIX_DAYS));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        Supplier supplier = new Supplier(name, phone, email, address, tagList, items, days, note);

        return new AddSupplierCommand(supplier);
    }
}
