package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_LENGTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_SYMBOL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NOTES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POINTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POINTS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AMY_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POINTS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POINTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCustomerCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CustomerBuilder;

public class AddCommandParserTest {
    private static final String CUSTOMER_COMMAND = Person.ContactType.CUSTOMER.lowerCase() + " ";
    private static final String CUSTOMER_COMMAND_UPPER = CUSTOMER_COMMAND.toUpperCase();
    private static final String STAFF_COMMAND = Person.ContactType.STAFF.lowerCase() + " ";
    private static final String STAFF_COMMAND_UPPER = STAFF_COMMAND.toUpperCase();
    private static final String SUPPLIER_COMMAND = Person.ContactType.SUPPLIER.lowerCase() + " ";
    private final AddCommandParser parser = new AddCommandParser();

    // Test cases for Customer
    @Test
    public void parseCustomer_allFieldsPresent_success() {
        Customer expectedCustomer = new CustomerBuilder(AMY).build();

        assertParseSuccess(parser, CUSTOMER_COMMAND + PREAMBLE_WHITESPACE + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY + POINTS_DESC_AMY,
                new AddCustomerCommand(expectedCustomer));


        // multiple tags - all accepted
        Customer expectedCustomerMultipleTags = new CustomerBuilder(AMY).withTags(VALID_TAG_AMY, VALID_TAG_AMY_2)
                .build();
        assertParseSuccess(parser,
                CUSTOMER_COMMAND + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + TAG_DESC_AMY + TAG_DESC_AMY_2
                        + NOTE_DESC_AMY + POINTS_DESC_AMY,
                new AddCustomerCommand(expectedCustomerMultipleTags));

        // non-lowercase add customer command
        assertParseSuccess(parser, CUSTOMER_COMMAND_UPPER + PREAMBLE_WHITESPACE + NAME_DESC_AMY
                        + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NOTE_DESC_AMY + POINTS_DESC_AMY,
                new AddCustomerCommand(expectedCustomer));
    }

    @Test
    public void parseCustomer_repeatedNonTagValue_failure() {
        String validExpectedCustomerString = CUSTOMER_COMMAND + PREAMBLE_WHITESPACE + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_AMY + NOTE_DESC_AMY + POINTS_DESC_AMY;

        // multiple names
        assertParseFailure(parser, CUSTOMER_COMMAND + NAME_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, CUSTOMER_COMMAND + PHONE_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, CUSTOMER_COMMAND + EMAIL_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, CUSTOMER_COMMAND + ADDRESS_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple points
        assertParseFailure(parser, CUSTOMER_COMMAND + POINTS_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POINTS));

        // multiple fields repeated
        assertParseFailure(parser,
                CUSTOMER_COMMAND + validExpectedCustomerString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + ADDRESS_DESC_AMY + POINTS_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE,
                        PREFIX_POINTS));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, CUSTOMER_COMMAND + INVALID_NAME_SYMBOL_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, CUSTOMER_COMMAND + INVALID_EMAIL_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, CUSTOMER_COMMAND + INVALID_PHONE_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, CUSTOMER_COMMAND + INVALID_ADDRESS_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid address
        assertParseFailure(parser, CUSTOMER_COMMAND + INVALID_POINTS_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POINTS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, CUSTOMER_COMMAND + validExpectedCustomerString + INVALID_NAME_SYMBOL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, CUSTOMER_COMMAND + validExpectedCustomerString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, CUSTOMER_COMMAND + validExpectedCustomerString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, CUSTOMER_COMMAND + validExpectedCustomerString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid points
        assertParseFailure(parser, CUSTOMER_COMMAND + validExpectedCustomerString + INVALID_POINTS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POINTS));
    }

    @Test
    public void parseCustomer_optionalFieldsMissing_success() {
        // zero tags
        Customer expectedAmy = new CustomerBuilder(AMY).withTags().build();
        assertParseSuccess(parser, CUSTOMER_COMMAND + NAME_DESC_AMY
                        + PHONE_DESC_AMY + EMAIL_DESC_AMY + NOTE_DESC_AMY
                        + ADDRESS_DESC_AMY,
                new AddCustomerCommand(expectedAmy));

        // no notes
        Customer expectedAlice1 = new CustomerBuilder(ALICE).withPoints(VALID_POINTS_AMY).withTags(VALID_TAG_AMY)
                .build();
        assertParseSuccess(parser, CUSTOMER_COMMAND + NAME_DESC_ALICE
                        + PHONE_DESC_ALICE + EMAIL_DESC_ALICE
                        + ADDRESS_DESC_ALICE + POINTS_DESC_AMY
                        + TAG_DESC_AMY,
                new AddCustomerCommand(expectedAlice1));

        // no points
        Customer expectedAlice2 = new CustomerBuilder(ALICE).withNote(VALID_NOTE_AMY).withTags(VALID_TAG_AMY)
                .build();
        assertParseSuccess(parser, CUSTOMER_COMMAND + NAME_DESC_ALICE
                        + PHONE_DESC_ALICE + EMAIL_DESC_ALICE
                        + ADDRESS_DESC_ALICE + NOTE_DESC_AMY
                        + TAG_DESC_AMY,
                new AddCustomerCommand(expectedAlice2));
    }

    @Test
    public void parseCustomer_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, CUSTOMER_COMMAND + VALID_NAME_AMY
                        + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, CUSTOMER_COMMAND
                        + NAME_DESC_AMY + VALID_PHONE_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, CUSTOMER_COMMAND
                        + NAME_DESC_AMY + PHONE_DESC_AMY
                        + VALID_EMAIL_AMY + ADDRESS_DESC_AMY,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, CUSTOMER_COMMAND
                        + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + VALID_ADDRESS_AMY,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, CUSTOMER_COMMAND
                        + VALID_NAME_AMY + VALID_PHONE_AMY
                        + VALID_EMAIL_AMY + VALID_ADDRESS_AMY,
                expectedMessage);
    }

    @Test
    public void parseCustomer_invalidValue_failure() {
        // invalid name - special symbols
        assertParseFailure(parser, CUSTOMER_COMMAND
                + INVALID_NAME_SYMBOL_DESC + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_AMY + TAG_DESC_AMY_2, Name.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, CUSTOMER_COMMAND
                + INVALID_NAME_LENGTH_DESC + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_AMY + TAG_DESC_AMY_2, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, CUSTOMER_COMMAND
                + NAME_DESC_AMY + INVALID_PHONE_DESC
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + TAG_DESC_AMY + TAG_DESC_AMY_2, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, CUSTOMER_COMMAND
                + NAME_DESC_AMY + PHONE_DESC_AMY
                + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                + TAG_DESC_AMY + TAG_DESC_AMY_2, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, CUSTOMER_COMMAND
                + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
                + TAG_DESC_AMY + TAG_DESC_AMY_2, Address.MESSAGE_CONSTRAINTS);

        // invalid note
        assertParseFailure(parser, CUSTOMER_COMMAND
                + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_NOTES_DESC
                + TAG_DESC_AMY + TAG_DESC_AMY_2, Note.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, CUSTOMER_COMMAND
                + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC + VALID_TAG_AMY, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, CUSTOMER_COMMAND
                + INVALID_NAME_SYMBOL_DESC
                + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + INVALID_ADDRESS_DESC, Name.MESSAGE_CONSTRAINTS);
    }
}
