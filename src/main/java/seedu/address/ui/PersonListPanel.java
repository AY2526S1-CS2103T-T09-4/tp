package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String CUSTOMER_CLASS = "customer-card";
    private static final String STAFF_CLASS = "staff-card";
    private static final String SUPPLIER_CLASS = "supplier-card";

    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            getStyleClass().removeAll(CUSTOMER_CLASS, STAFF_CLASS, SUPPLIER_CLASS);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                PersonCard result;
                int index = getIndex() + 1;
                String contactType = person.getContactType().toString().toLowerCase();

                switch(contactType) {
                case "customer":
                    getStyleClass().add(CUSTOMER_CLASS);
                    result = new CustomerCard(person, index);
                    break;
                case "staff":
                    getStyleClass().add(STAFF_CLASS);
                    result = new StaffCard(person, index);
                    break;
                case "supplier":
                    getStyleClass().add(SUPPLIER_CLASS);
                    result = new SupplierCard(person, index);
                    break;
                default:
                    throw new IllegalStateException("Unexpected type: " + contactType);
                }
                setGraphic(result.getRoot());
            }
        }
    }
}
