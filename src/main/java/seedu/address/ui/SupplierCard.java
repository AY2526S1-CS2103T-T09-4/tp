package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Supplier}.
 */
public class SupplierCard extends PersonCard {

    private static final String FXML = "SupplierListCard.fxml";

    @FXML
    protected Label items;
    @FXML
    protected Label days;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public SupplierCard(Person person, int displayedIndex) {
        super(person, displayedIndex, FXML);
        System.out.println(person.getItems().toString());
        type.getStyleClass().add("type_supplier");
        items.setText(person.getItems().toString());
        days.setText(person.getDays().toString());
    }
}
