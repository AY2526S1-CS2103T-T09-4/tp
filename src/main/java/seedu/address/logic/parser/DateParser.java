package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Utility class for parsing dates in d/M/yyyy format.
 */
public class DateParser {
    public static final String MESSAGE_FORMAT_CONSTRAINT = "Invalid date format,"
            + " Expected format: d/M/yyyy";
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy").withResolverStyle(ResolverStyle.SMART);

    /**
     * Parses a date string in the format d/M/yyyy into a LocalDate.
     *
     * <p>Examples of valid input:
     * <ul>
     *   <li>"1/1/2025"</li>
     *   <li>"28/10/2025"</li>
     * </ul>
     *
     * @param input the date string to parse, not null
     * @return the parsed LocalDate
     * @throws ParseException if the input cannot be parsed
     */
    public static LocalDate parseDate(String input) throws ParseException {
        try {
            return LocalDate.parse(input.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_FORMAT_CONSTRAINT);
        }
    }
}
