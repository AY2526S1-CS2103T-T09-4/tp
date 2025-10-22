package seedu.address.testutil;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.customer.Points;

/**
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder extends PersonBuilder<Customer> {
    public static final String DEFAULT_POINTS = CommandTestUtil.VALID_POINTS_AMY;

    private Points points = new Points(0);

    /**
     * Creates a {@code CustomerBuilder} with the default details.
     */
    public CustomerBuilder() {
        super();
        try {
            points = ParserUtil.parsePoints(DEFAULT_POINTS);
        } catch (ParseException e) {
            // Fallthrough
        }
    }

    /**
     * Initializes the CustomerBuilder with the data of {@code customerToCopy}.
     */
    public CustomerBuilder(Customer customerToCopy) {
        super(customerToCopy);
        points = customerToCopy.getPoints();
    }

    @Override
    public CustomerBuilder withPoints(String points) {
        try {
            this.points = ParserUtil.parsePoints(points);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid points format in CustomerBuilder", e);
        }
        return this;
    }

    /**
     * Builds a new {@code Customer} by taking in the relevant fields and outputting an object.
     */
    @Override
    public Customer build() {
        return new Customer(name, phone, email, address, points, tags, note);
    }
}
