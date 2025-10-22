package seedu.address.testutil;

import seedu.address.model.person.customer.Customer;

/**
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder extends PersonBuilder<Customer> {

    /**
     * Creates a {@code CustomerBuilder} with the default details.
     */
    public CustomerBuilder() {
        super();
    }

    /**
     * Initializes the CustomerBuilder with the data of {@code customerToCopy}.
     */
    public CustomerBuilder(Customer customerToCopy) {
        super(customerToCopy);
    }

    /**
     * Builds a new {@code Customer} by taking in the relevant fields and outputting an object.
     */
    @Override
    public Customer build() {
        return new Customer(name, phone, email, address, tags, note);
    }
}
