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

    private static boolean wordPrefixMatchIgnoreCase(String name, String keyword) {
        if (keyword == null) {
            return false;
        }
        String q = keyword.trim();
        if (q.length() < MIN_PREFIX_LEN) {
            return false;
        }
        Pattern p = Pattern.compile("(?i)\\b" + Pattern.quote(q));
        return p.matcher(name).find();
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
