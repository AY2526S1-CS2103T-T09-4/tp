package seedu.address.model.person.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class SupplierTest {
    private Name validName = new Name(PersonBuilder.DEFAULT_NAME);
    private Phone validPhone = new Phone(PersonBuilder.DEFAULT_PHONE);
    private Email validEmail = new Email(PersonBuilder.DEFAULT_EMAIL);
    private Address validAddress = new Address(PersonBuilder.DEFAULT_ADDRESS);
    private Set<Tag> tags = new HashSet<>();
    private Note validNote = new Note("");
    private List<Days> days;
    private List<Items> items;

    @BeforeEach
    public void setUp() {
        days = new ArrayList<>();
        days.add(new Days(LocalDate.of(2025, 10, 15)));
        days.add(new Days(LocalDate.of(2025, 10, 20)));
        items = new ArrayList<>();
        items.add(new Items("egg"));
        items.add(new Items("milk"));
    }

    @Test
    public void constructor_validInputs_success() {
        Supplier supplier = new Supplier(validName, validPhone, validEmail, validAddress, tags, items, days, validNote);

        assertEquals(validName, supplier.getName());
        assertEquals(validPhone, supplier.getPhone());
        assertEquals(validEmail, supplier.getEmail());
        assertEquals(validAddress, supplier.getAddress());
        assertEquals(tags, supplier.getTags());
        assertEquals(days, supplier.getDays());
        assertEquals(items, supplier.getItems());
    }

    @Test
    public void constructor_defensiveCopy_daysListNotAliased() {
        Supplier supplier = new Supplier(validName, validPhone, validEmail, validAddress, tags, items, days, validNote);

        days.add(new Days(LocalDate.of(2025, 11, 1)));

        assertEquals(2, supplier.getDays().size());
    }

    @Test
    public void constructor_defensiveCopy_shiftsListNotAliased() {
        Supplier supplier = new Supplier(validName, validPhone, validEmail, validAddress, tags, items, days, validNote);

        items.add(new Items("Coffee"));

        assertEquals(2, supplier.getItems().size());
    }
}
