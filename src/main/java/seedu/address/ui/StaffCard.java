package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Staff}.
 */
public class StaffCard extends PersonCard {

    private static final String FXML = "StaffListCard.fxml";

    @FXML
    protected Label shifts;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public StaffCard(Person person, int displayedIndex) {
        super(person, displayedIndex, FXML);
        type.getStyleClass().add("type_staff");
        shifts.setText(person.getShifts().toString());
    }
}

