package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Supplier;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    // Customers
    public static final Customer ALICE = new CustomerBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").build();
    public static final Customer BENSON = new CustomerBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("frequentCustomer").withNote("allergic to nuts").build();
    public static final Customer AMY = new CustomerBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).build();

    // Staff
    public static final Staff CARL = new StaffBuilder().withName(VALID_NAME_CARL).withPhone(VALID_PHONE_CARL)
            .withEmail(VALID_EMAIL_CARL).withAddress(VALID_ADDRESS_CARL).withShifts(VALID_SHIFTS_CARL).build();;
    public static final Staff DANIEL = new StaffBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();;

    // Supplier
    public static final Supplier ELLE = new SupplierBuilder().withName(VALID_NAME_ELLE).withPhone(VALID_PHONE_ELLE)
                    .withEmail(VALID_EMAIL_ELLE).withAddress(VALID_ADDRESS_ELLE).withItems(VALID_ITEMS_ELLE)
                    .withDays(VALID_DAYS_ELLE).build();
    public static final Supplier FIONA = new SupplierBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();;

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA));
    }
}
