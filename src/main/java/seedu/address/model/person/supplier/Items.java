package seedu.address.model.person.supplier;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an item that Suppliers can provide, in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Items {
    public static final String MESSAGE_COMPULSORY = "At least one item must be provided.";
    public static final String MESSAGE_CONSTRAINTS = "Item must be non empty, and contain only letters and numbers.";
    public static final String MESSAGE_DUPLICATE_CONSTRAINTS = "Duplicates items are not allowed: ";

    private static final String VALIDATION_REGEX = "[A-Za-z0-9]+(?: [A-Za-z0-9]+)*";

    private final String name;

    /**
     * Constructor that creates Item class with name.
     */
    public Items(String name) {
        requireAllNonNull(name);
        String trimmed = name.trim();
        if (trimmed.isEmpty() || !trimmed.matches(VALIDATION_REGEX)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.name = trimmed;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Items)) {
            return false;
        }

        Items otherItems = (Items) other;
        return name.equalsIgnoreCase(otherItems.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
