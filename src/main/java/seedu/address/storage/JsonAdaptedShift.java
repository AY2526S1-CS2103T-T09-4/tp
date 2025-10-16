package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.staff.Shift;

/**
 * Jackson-friendly version of {@link Shift}.
 */
class JsonAdaptedShift {

    private final String shiftDate;


    /**
     * Constructs a {@code JsonAdaptedShift} with the given {@code shiftDate}.
     */
    @JsonCreator
    public JsonAdaptedShift(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    /**
     * Converts a given {@code Shift} into this class for Jackson use.
     */
    public JsonAdaptedShift(Shift source) {
        this.shiftDate = source.toString(); // Shift#toString returns yyyy-MM-dd
    }

    @JsonValue
    public String getShiftDate() {
        return shiftDate;
    }

    /**
     * Converts this Jackson-friendly adapted shift object into the model's {@code Shift} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted shift.
     */
    public Shift toModelType() throws IllegalValueException {
        if (shiftDate == null) {
            throw new IllegalValueException(Shift.MESSAGE_COMPULSORY);
        }
        try {
            LocalDate parsedDate = LocalDate.parse(shiftDate);
            return new Shift(parsedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Shift.MESSAGE_CONSTRAINTS);
        }
    }
}
