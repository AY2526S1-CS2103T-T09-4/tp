package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class DateParserTest {

    @Test
    public void parseDate_validInput_success() throws ParseException {
        assertEquals(LocalDate.of(2025, 10, 28),
                DateParser.parseDate("28/10/2025"));
    }

    @Test
    public void parseDate_invalidFormat_throwsIllegalArgumentException() {
        assertThrows(ParseException.class, () -> DateParser.parseDate("2025-10-28")); // wrong delimiter
    }

    @Test
    public void parseDate_nonExistentDate_throwsIllegalArgumentException() throws ParseException {
        assertEquals(LocalDate.of(2025, 2, 28), DateParser.parseDate("31/02/2025")); // Feb 31 doesn't exist
    }

    @Test
    public void parseDate_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateParser.parseDate(null));
    }
}
