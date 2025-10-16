package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.supplier.Days;

/**
 * Jackson-friendly version of {@link Days}.
 */
class JsonAdaptedDays {

    private final String day;

    /**
     * Constructs a {@code JsonAdaptedDays} with the given {@code day}.
     */
    @JsonCreator
    public JsonAdaptedDays(String day) {
        this.day = day;
    }

    /**
     * Converts a given {@code Days} into this class for Jackson use.
     */
    public JsonAdaptedDays(Days source) {
        this.day = source.toString(); 
    }

    @JsonValue
    public String getDay() {
        return day;
    }

    /**
     * Converts this Jackson-friendly adapted object into the model's {@code Days}.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public Days toModelType() throws IllegalValueException {
        if (day == null) {
            throw new IllegalValueException(Days.MESSAGE_COMPULSORY);
        }
        try {
            LocalDate parsed = LocalDate.parse(day);
            return new Days(parsed);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Days.MESSAGE_CONSTRAINTS);
        }
    }
}
