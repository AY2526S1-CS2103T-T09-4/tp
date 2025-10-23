package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalPersons;

public class AllOfPersonPredicatesTest {

    @Test
    public void test_allMustMatch() {
        Person alice = TypicalPersons.ALICE;   // name: "Alice Pauline", address contains "Jurong"
        Person benson = TypicalPersons.BENSON;

        FieldContainsKeywordsPredicate nameIsAlice = new FieldContainsKeywordsPredicate(
                p -> p.getName().fullName, List.of("Alice"), true);
        FieldContainsKeywordsPredicate addressHasJurong = new FieldContainsKeywordsPredicate(
                p -> p.getAddress().value, List.of("Jurong"), false);

        AllOfPersonPredicates and = new AllOfPersonPredicates(
                List.of(nameIsAlice, addressHasJurong));

        assertTrue(and.test(alice));    // both true
        assertFalse(and.test(benson));  // name predicate fails
    }
}
