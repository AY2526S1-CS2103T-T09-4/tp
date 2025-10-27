package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s extracted String field matches any of the given keywords.
 * Matching is case-insensitive. When {@code wordMatch} is true, whole-word semantics are used;
 * otherwise, substring semantics are used.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Person> {
    private final Function<Person, String> extractor;
    private final List<String> keywords;
    private final boolean wordMatch;

    /**
     * Constructs a predicate for case-insensitive matching against an extracted String field.
     *
     * @param extractor function to extract the field string from a Person (non-null)
     * @param keywords  list of keywords; match is OR within this list (non-null)
     * @param wordMatch if true, use whole-word semantics; otherwise, substring semantics
     */
    public FieldContainsKeywordsPredicate(Function<Person, String> extractor,
                                          List<String> keywords,
                                          boolean wordMatch) {
        this.extractor = Objects.requireNonNull(extractor, "extractor");
        this.keywords = Objects.requireNonNull(keywords, "keywords");
        this.wordMatch = wordMatch;
    }

    @Override
    public boolean test(Person person) {
        String field = extractor.apply(person);
        final String text = (field == null) ? "" : field;
        if (wordMatch) {
            return keywords.stream()
                    .anyMatch(kw -> StringUtil.containsWordIgnoreCase(text, kw));
        } else {
            return keywords.stream()
                    .anyMatch(kw -> containsIgnoreCase(text, kw));
        }
    }


    private static boolean containsIgnoreCase(String text, String query) {
        if (query == null || query.isBlank()) {
            return false;
        }
        return text.toLowerCase().contains(query.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FieldContainsKeywordsPredicate)) {
            return false;
        }
        FieldContainsKeywordsPredicate o = (FieldContainsKeywordsPredicate) other;
        return extractor.equals(o.extractor)
                && keywords.equals(o.keywords)
                && wordMatch == o.wordMatch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(extractor, keywords, wordMatch);
    }

    @Override
    public String toString() {
        return "FieldContainsKeywordsPredicate[keywords=" + keywords
                + ", wordMatch=" + wordMatch + "]";
    }
}
