package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private static final int MIN_PREFIX_LEN = 1;
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        final String name = person.getName().fullName;
        return keywords.stream().anyMatch(k -> wordPrefixMatchIgnoreCase(name, k));
    }

    private static boolean wordPrefixMatchIgnoreCase(String haystack, String needle) {
        if (needle == null) return false;
        String q = needle.trim();
        if (q.length() < MIN_PREFIX_LEN) return false; // avoid super-broad 1-char matches
        // \b = word boundary; we only require the *prefix* after the boundary
        Pattern p = Pattern.compile("(?i)\\b" + Pattern.quote(q));
        return p.matcher(haystack).find();
    }


    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NameContainsKeywordsPredicate
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
