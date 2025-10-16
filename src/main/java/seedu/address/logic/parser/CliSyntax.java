package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ITEMS = new Prefix("items/");
    public static final Prefix PREFIX_DAYS = new Prefix("days/");
    public static final Prefix PREFIX_SHIFTS = new Prefix("shifts/");
    public static final Prefix PREFIX_NOTE = new Prefix("notes/");

    public static final String CUSTOMER_TYPE = "customer";
    public static final String STAFF_TYPE = "staff";
    public static final String SUPPLIER_TYPE = "supplier";
}
