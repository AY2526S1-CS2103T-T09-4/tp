package seedu.address.ui;

import java.util.stream.Collectors;

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
        type.getStyleClass().add("type_supplier");
        String formattedDays = person.getDays().stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.joining(", "));
        String formattedItems = person.getItems().stream()
                .map(Object::toString)
                .sorted()
                .collect(Collectors.joining(", "));
        items.setText(formattedItems);
        days.setText(formattedDays);
    }
}
