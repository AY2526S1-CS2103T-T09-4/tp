package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ListCommand.Category;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.staff.Shift;
import seedu.address.model.person.supplier.Days;
import seedu.address.model.person.supplier.Items;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_CATEGORY = "Invalid category.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String note} into an {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        String trimmedNote = note.trim();
        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

    /**
     * Parses {@code List<String> shiftStrings} into a {@code List<Shift>}.
     */
    public static List<Shift> parseShifts(List<String> shiftStrings) throws ParseException {
        List<Shift> shifts = new ArrayList<>();
        if (shiftStrings.isEmpty()) {
            throw new ParseException(Shift.MESSAGE_COMPULSORY);
        }

        String raw = shiftStrings.get(0);
        for (String token : raw.split(", ")) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                LocalDate date = LocalDate.parse(trimmed);
                shifts.add(new Shift(date));
            } catch (DateTimeParseException e) {
                throw new ParseException(Shift.MESSAGE_CONSTRAINTS);
            }
        }
        return shifts;
    }

    /**
     * Parses {@code List<String> daysString} into a {@code List<Days>}.
     */
    public static List<Days> parseDays(List<String> daysStrings) throws ParseException {
        List<Days> days = new ArrayList<>();
        if (daysStrings.isEmpty()) {
            throw new ParseException(Shift.MESSAGE_COMPULSORY);
        }

        String raw = daysStrings.get(0);
        for (String token : raw.split(", ")) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                LocalDate date = LocalDate.parse(trimmed);
                days.add(new Days(date));
            } catch (DateTimeParseException e) {
                throw new ParseException(Shift.MESSAGE_CONSTRAINTS);
            }
        }
        return days;
    }

    /**
     * Parses {@code List<String> itemStrings} into a {@code List<Items>}.
     */
    public static List<Items> parseItems(List<String> itemStrings) throws ParseException {
        List<Items> items = new ArrayList<>();
        if (itemStrings == null || itemStrings.isEmpty()) {
            throw new ParseException(Items.MESSAGE_COMPULSORY);
        }

        String raw = itemStrings.get(0);
        for (String token : raw.split(", ")) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                items.add(new Items(trimmed));
            } catch (IllegalArgumentException e) {
                throw new ParseException(Items.MESSAGE_CONSTRAINTS);
            }
        }
        if (items.isEmpty()) {
            throw new ParseException(Items.MESSAGE_COMPULSORY);
        }
        return items;
    }


    /**
     * Parses {@code String categoryStr} into a {@code Category}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code categoryStr} is invalid
     */
    public static Category parseCategory(String categoryStr) throws ParseException {
        requireNonNull(categoryStr);
        String trimmedLowerCaseCategory = categoryStr.trim().toLowerCase();
        Category category = Category.fromString(trimmedLowerCaseCategory);

        if (category == null) {
            throw new ParseException(MESSAGE_INVALID_CATEGORY);
        }

        return category;
    }
}
