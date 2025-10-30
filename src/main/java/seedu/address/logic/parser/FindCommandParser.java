package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AllOfPersonPredicates;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final List<Prefix> ALLOWED_PREFIXES = List.of(
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_TAG, PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS
    );

    private static final java.util.Set<String> ALLOWED_PREFIX_STRINGS =
            ALLOWED_PREFIXES.stream().map(Prefix::getPrefix).collect(java.util.stream.Collectors.toSet());

    private static java.util.List<String> getUnknownPrefixes(String raw) {
        java.util.List<String> invalid = new java.util.ArrayList<>();
        for (String tok : raw.trim().split("\\s+")) {
            int slash = tok.indexOf('/');
            if (slash > 0) {
                String maybe = tok.substring(0, slash + 1); // e.g., "x/"
                if (maybe.matches("[A-Za-z]+/") && !ALLOWED_PREFIX_STRINGS.contains(maybe)) {
                    invalid.add(maybe);
                }
            }
        }
        return invalid;
    }

    @Override
    public FindCommand parse(String args) throws ParseException {
        final String raw = args == null ? "" : args.trim();
        if (raw.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        var unknown = getUnknownPrefixes(raw);
        if (!unknown.isEmpty()) {
            throw new ParseException("Unknown prefix(es): " + String.join(", ", unknown));
        }

        ArgumentMultimap map = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS
        );

        map.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS, PREFIX_TAG
        );

        List<Predicate<Person>> perField = new ArrayList<>();
        Function<String, List<String>> toKeywords = s -> Arrays.stream(s.trim().split("\\s+"))
                .filter(k -> !k.isBlank())
                .collect(Collectors.toList());

        // Common to all Person
        addIfPresent(map, PREFIX_NAME, toKeywords,
                kws -> (Predicate<Person>) new NameContainsKeywordsPredicate(kws), perField);
        addIfPresent(map, PREFIX_PHONE, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> p.getPhone().value, kws, false), perField);
        addIfPresent(map, PREFIX_EMAIL, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> p.getEmail().value, kws, false), perField);
        map.getAllValues(PREFIX_ADDRESS).stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .reduce((a, b) -> a + " " + b)
                .ifPresent(rawPhrase -> {
                    String needle = norm(rawPhrase);
                    perField.add((Person p) -> {
                        String hay = norm(p.getAddress().value);
                        return hay.contains(needle);
                    });
                });

        addIfPresent(map, PREFIX_TAG, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(
                        p -> p.getTags().stream().map(tag -> tag.tagName).collect(Collectors.joining(" ")),
                        kws, false),
                perField);

        // Supplier only fields: items, days
        addIfPresent(map, PREFIX_ITEMS, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> {
                    if (p.getContactType() != Person.ContactType.SUPPLIER) {
                        return "";
                    }
                    return p.getItems().stream().map(Object::toString).collect(Collectors.joining(" "));
                }, kws, false),
                perField);

        addIfPresent(map, PREFIX_DAYS, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> {
                    if (p.getContactType() != Person.ContactType.SUPPLIER) {
                        return "";
                    }
                    return p.getDays().stream().map(Object::toString).collect(Collectors.joining(" "));
                }, kws, false),
                perField);

        // Staff only field: shifts
        addIfPresent(map, PREFIX_SHIFTS, toKeywords,
                kws -> new FieldContainsKeywordsPredicate(p -> {
                    if (p.getContactType() != Person.ContactType.STAFF) {
                        return "";
                    }
                    return p.getShifts().stream().map(Objects::toString).collect(Collectors.joining(" "));
                }, kws, false),
                perField);

        // Reject empty values for any provided prefix
        for (Prefix px : List.of(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_ITEMS, PREFIX_DAYS, PREFIX_SHIFTS)) {
            if (map.getValue(px).filter(v -> v.trim().isEmpty()).isPresent()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        if (perField.isEmpty()) {
            String preamble = map.getPreamble().trim();
            if (preamble.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            List<String> kws = toKeywords.apply(preamble);
            if (kws.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            perField.add(new NameContainsKeywordsPredicate(kws));
        }

        return new FindCommand(new AllOfPersonPredicates(perField));
    }

    private static String norm(String s) {
        return s.toLowerCase()
                .replaceAll("[^\\p{Alnum}]+", " ")
                .replaceAll("\\s+", " ")
                .trim();
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

