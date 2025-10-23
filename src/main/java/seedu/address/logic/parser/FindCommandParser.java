package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AllOfPersonPredicates;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.staff.Staff;
import seedu.address.model.person.supplier.Supplier;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Prefix PREFIX_NAME = new Prefix("n/");
    private static final Prefix PREFIX_PHONE = new Prefix("p/");
    private static final Prefix PREFIX_EMAIL = new Prefix("e/");
    private static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    private static final Prefix PREFIX_TAG = new Prefix("t/");
    private static final Prefix PREFIX_SHIFTS = new Prefix("shifts/");
    private static final Prefix PREFIX_ITEMS = new Prefix("items/");
    private static final Prefix PREFIX_DAYS = new Prefix("days/");

    @Override
    public FindCommand parse(String args) throws ParseException {
        final String raw = args == null ? "" : args.trim();
        if (raw.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap map = ArgumentTokenizer.tokenize(
                raw,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS
        );

        List<Prefix> all = List.of(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS
        );

        boolean anyPrefixEmpty = all.stream().anyMatch(px ->
                map.getAllValues(px).stream().anyMatch(v -> v.trim().isEmpty()));
        if (anyPrefixEmpty) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        boolean anyPrefixPresent = Stream.of(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS
        ).anyMatch(px -> map.getValue(px).isPresent());

        if (!anyPrefixPresent) {
            String preamble = map.getPreamble().trim();
            if (preamble.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            List<String> keywords = Arrays.stream(preamble.split("\\s+"))
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toList());
            return new FindCommand(new NameContainsKeywordsPredicate(keywords));
        }

        List<Predicate<Person>> perField = new ArrayList<>();
        Function<String, List<String>> toKeywords = s -> Arrays.stream(s.trim().split("\\s+"))
                .filter(k -> !k.isBlank()).collect(Collectors.toList());

        // Common to all Person
        addIfPresent(map, PREFIX_NAME, toKeywords,
                kws -> (Predicate<Person>) new NameContainsKeywordsPredicate(kws), perField);
        addIfPresent(map, PREFIX_PHONE, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> p.getPhone().value, kws, false), perField);
        addIfPresent(map, PREFIX_EMAIL, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> p.getEmail().value, kws, false), perField);
        addIfPresent(map, PREFIX_ADDRESS, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> p.getAddress().value, kws, false), perField);
        addIfPresent(map, PREFIX_TAG, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(
                        p -> p.getTags().stream().map(tag -> tag.tagName).collect(Collectors.joining(" ")),
                        kws, false),
                perField);

        // Supplier only fields: items, days
        addIfPresent(map, PREFIX_ITEMS, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> {
                    if (!(p instanceof Supplier s)) {
                        return "";
                    }
                    return s.getItems().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(" "));
                }, kws, false),
                perField);

        addIfPresent(map, PREFIX_DAYS, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> {
                    if (!(p instanceof Supplier s)) {
                        return "";
                    }
                    return s.getDays().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(" "));
                }, kws, false),
                perField);

        // Staff only field: shifts
        addIfPresent(map, PREFIX_SHIFTS, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> {
                    if (!(p instanceof Staff s)) {
                        return "";
                    }
                    return s.getShifts().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(" "));
                }, kws, false),
                perField);

        if (perField.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        for (Prefix px : List.of(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS)) {
            if (map.getValue(px).filter(v -> v.trim().isEmpty()).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        return new FindCommand(new AllOfPersonPredicates(perField));
    }

    private void addIfPresent(ArgumentMultimap map, Prefix prefix,
                              Function<String, List<String>> toKeywords,
                              Function<List<String>, Predicate<Person>> makePredicate,
                              List<Predicate<Person>> out) {
        map.getValue(prefix).ifPresent(raw -> {
            List<String> kws = toKeywords.apply(raw);
            if (!kws.isEmpty()) {
                out.add(makePredicate.apply(kws));
            }
        });
    }
}
