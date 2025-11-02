package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's note in the address book.
 * Guarantees: immutable;
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS = "Notes can take any values not exceeding 200 characters";

    private static final int MAX_LENGTH = 200;

    public final String value;

    /**
     * Constructs a {@code note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_CONSTRAINTS);
        value = note;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Note)
                && value.equals(((Note) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
