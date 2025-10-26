package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalPersons;

public class FieldContainsKeywordsPredicateTest {

    @Test
    public void test_wordMatch_nameWholeWord() {
        Person alice = TypicalPersons.ALICE;
        FieldContainsKeywordsPredicate pred = new FieldContainsKeywordsPredicate(
                p -> p.getName().fullName, List.of("Alice"), true);

        assertTrue(pred.test(alice));
        FieldContainsKeywordsPredicate no = new FieldContainsKeywordsPredicate(
                p -> p.getName().fullName, List.of("Ali"), true);
        assertFalse(no.test(alice));
    }

    @Test
    public void test_substring_email() {
        Person alice = TypicalPersons.ALICE;
        Person benson = TypicalPersons.BENSON;

        FieldContainsKeywordsPredicate pred = new FieldContainsKeywordsPredicate(
                p -> p.getEmail().value, List.of("alice@"), false);

        assertTrue(pred.test(alice));
        assertFalse(pred.test(benson));
    }

    @Test
    public void test_address_caseInsensitive() {
        Person alice = TypicalPersons.ALICE;
        FieldContainsKeywordsPredicate pred = new FieldContainsKeywordsPredicate(
                p -> p.getAddress().value, List.of("jurong"), false);

        assertTrue(pred.test(alice));
    }

    @Test
    public void constructor_nullExtractor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new FieldContainsKeywordsPredicate(null, List.of("x"), false));
    }

    @Test
    public void constructor_nullKeywords_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new FieldContainsKeywordsPredicate(p -> p.getPhone().value, null, false));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        var extractor = (java.util.function.Function<Person, String>) (p -> p.getPhone().value);
        var keywords = java.util.List.of("1", "2");

        var a = new FieldContainsKeywordsPredicate(extractor, keywords, false);
        var b = new FieldContainsKeywordsPredicate(extractor, keywords, false);

        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        var a = new FieldContainsKeywordsPredicate(p -> p.getPhone().value, List.of("1", "2"), false);
        var b = new FieldContainsKeywordsPredicate(p -> p.getPhone().value, List.of("1", "3"), false);
        assertNotEquals(a, b);
    }

    @Test
    public void equals_differentExtractor_returnsFalse() {
        var exPhone = (java.util.function.Function<Person, String>) (p -> p.getPhone().value);
        var exEmail = (java.util.function.Function<Person, String>) (p -> p.getEmail().value);

        var a = new FieldContainsKeywordsPredicate(exPhone, java.util.List.of("x"), false);
        var b = new FieldContainsKeywordsPredicate(exEmail, java.util.List.of("x"), false);

        assertNotEquals(a, b);
    }


    @Test
    public void equals_differentWordMatch_returnsFalse() {
        var a = new FieldContainsKeywordsPredicate(p -> p.getPhone().value, List.of("123"), false);
        var b = new FieldContainsKeywordsPredicate(p -> p.getPhone().value, List.of("123"), true);
        assertNotEquals(a, b);
    }

    @Test
    public void toString_matchesFormat() {
        var pred = new FieldContainsKeywordsPredicate(p -> p.getPhone().value, List.of("a", "b"), true);
        String s = pred.toString();
        assertTrue(s.startsWith("FieldContainsKeywordsPredicate["));
        assertTrue(s.contains("keywords=[a, b]"));
        assertTrue(s.contains("wordMatch=true"));
    }

    @Test
    public void equals_null_returnsFalse() {
        var extractor = (java.util.function.Function<Person, String>) (p -> p.getPhone().value);
        var a = new FieldContainsKeywordsPredicate(extractor, java.util.List.of("1", "2"), false);
        assertFalse(a.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        var extractor = (java.util.function.Function<Person, String>) (p -> p.getPhone().value);
        var a = new FieldContainsKeywordsPredicate(extractor, java.util.List.of("1", "2"), false);
        assertFalse(a.equals("not a predicate"));
    }

}
