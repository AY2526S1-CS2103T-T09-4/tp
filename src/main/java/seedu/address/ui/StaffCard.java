package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Staff}.
 */
public class StaffCard extends PersonCard {

    private static final String FXML = "StaffListCard.fxml";
    private static final int MAX_SLOTS = 3;

    @FXML
    private VBox shiftsBox; // container for the mini list of shifts
    @FXML
    private Label shiftsHeader;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public StaffCard(Person person, int displayedIndex) {
        super(person, displayedIndex, FXML);
        type.getStyleClass().add("type_staff");

        updateShifts(person);
    }

    private void updateShifts(Person person) {
        List<String> shifts = person.getShifts().stream()
                .map(Object::toString)
                .sorted()
                .limit(MAX_SLOTS)
                .toList();

        shiftsHeader.setText("Upcoming shifts:");

        // Add actual shifts
        for (String s : shifts) {
            Label l = new Label(s);
            l.getStyleClass().add("shift-entry");
            shiftsBox.getChildren().add(l);
        }

        // Fill remaining slots with blanks
        int blanks = MAX_SLOTS - shifts.size();
        for (int i = 0; i < blanks; i++) {
            Label placeholder = new Label("—");
            placeholder.getStyleClass().add("shift-entry empty");
            shiftsBox.getChildren().add(placeholder);
        }

    }
}

