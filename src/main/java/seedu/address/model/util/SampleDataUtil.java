package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.customer.Points;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Days;
import seedu.address.model.person.supplier.Items;
import seedu.address.model.person.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() throws ParseException {
        return new Person[] {
            new Customer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Points(3),
                getTagSet("frequentCustomer"), new Note("Loves coffee.")),
            new Customer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Points(0),
                getTagSet("prefersDelivery"), new Note("Loves cakes.")),
            new Staff(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("partTime"), getShiftList("12/12/2025, 17/12/2025"),
                    new Note("Works on weekdays only.")),
            new Staff(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("fullTime"), getShiftList("14/12/2025, 19/12/2025"),
                    new Note("Doesn't work on Mondays.")),
            new Supplier(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("preferredSupplier"),
                    getItemsList("Eggs, Flour"), getDaysList("13/12/2025, 18/12/2025"),
                    new Note("Call him 24 hours before.")),
            new Supplier(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("nextDayDelivery"),
                    getItemsList("Eggs, Flour"), getDaysList("13/12/2025, 18/12/2025"),
                    new Note("Ships from overseas.")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() throws ParseException {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static List<Shift> getShiftList(String ... strings) throws ParseException {
        return ParserUtil.parseShifts(Arrays.stream(strings)
                    .collect(Collectors.toList()));
    }

    public static List<Days> getDaysList(String ... strings) throws ParseException {
        return ParserUtil.parseDays(Arrays.stream(strings)
                .collect(Collectors.toList()));
    }

    public static List<Items> getItemsList(String ... strings) throws ParseException {
        return ParserUtil.parseItems(Arrays.stream(strings)
                .collect(Collectors.toList()));
    }
}
