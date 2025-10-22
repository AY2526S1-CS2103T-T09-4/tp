package seedu.address.testutil;

import java.util.List;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.supplier.Days;
import seedu.address.model.person.supplier.Items;
import seedu.address.model.person.supplier.Supplier;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Supplier objects.
 */
public class SupplierBuilder extends PersonBuilder<Supplier> {
    public static final String DEFAULT_ITEMS = CommandTestUtil.VALID_ITEMS_ELLE;
    public static final String DEFAULT_DAYS = CommandTestUtil.VALID_DAYS_ELLE;

    private List<Items> items;
    private List<Days> days;

    /**
     * Creates a {@code SupplierBuilder} with the default details.
     */
    public SupplierBuilder() {
        super();
        try {
            days = ParserUtil.parseDays(List.of(DEFAULT_DAYS));
            items = ParserUtil.parseItems(List.of(DEFAULT_ITEMS));
        } catch (ParseException e) {
            // Fallthrough
        }
    }

    /**
     * Initializes the SupplierBuilder with the data of {@code supplierToCopy}.
     */
    public SupplierBuilder(Supplier supplierToCopy) {
        super(supplierToCopy);
        days = supplierToCopy.getDays();
        items = supplierToCopy.getItems();
    }

    @Override
    public SupplierBuilder withDays(String ... days) {
        try {
            this.days = SampleDataUtil.getDaysList();
        } catch (ParseException e) {
            // Fallthrough
        }

        return this;
    }

    @Override
    public SupplierBuilder withItems(String ... items) {
        try {
            this.items = SampleDataUtil.getItemsList();
        } catch (ParseException e) {
            // Fallthrough
        }

        return this;
    }

    /**
     * Builds a new {@code Supplier} by taking in the relevant fields and outputting an object.
     */
    public Supplier build() {
        return new Supplier(name, phone, email, address, tags, items, days, note);
    }
}
