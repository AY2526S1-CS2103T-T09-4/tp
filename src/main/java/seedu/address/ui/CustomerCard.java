package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Customer}.
 */
public class CustomerCard extends PersonCard {

    private static final String FXML = "CustomerListCard.fxml";

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public CustomerCard(Person person, int displayedIndex) {
        super(person, displayedIndex, FXML);
        type.getStyleClass().add("type_customer");
    }
}
