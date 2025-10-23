package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void test_substring_address_caseInsensitive() {
        Person alice = TypicalPersons.ALICE;
        FieldContainsKeywordsPredicate pred = new FieldContainsKeywordsPredicate(
                p -> p.getAddress().value, List.of("jurong"), false);

        assertTrue(pred.test(alice));
    }
}
