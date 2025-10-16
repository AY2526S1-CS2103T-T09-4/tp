package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.supplier.Items;

/**
 * Jackson-friendly version of {@link Items}.
 */
class JsonAdaptedItems {

    private final String value;

    /**
     * Constructs a {@code JsonAdaptedItems} with the given {@code value}.
     */
    @JsonCreator
    public JsonAdaptedItems(String value) {
        this.value = value;
    }

    /**
     * Converts a given {@code Items} into this class for Jackson use.
     */
    public JsonAdaptedItems(Items source) {
        this.value = source.toString(); // Items#toString should return the canonical item string
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Converts this Jackson-friendly adapted object into the model's {@code Items}.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item.
     */
    public Items toModelType() throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException("Item value is missing!");
        }
        try {
            return new Items(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Items.MESSAGE_CONSTRAINTS);
        }
    }
}


