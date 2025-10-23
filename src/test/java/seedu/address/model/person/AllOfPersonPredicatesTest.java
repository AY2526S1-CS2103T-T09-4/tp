package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalPersons;

public class AllOfPersonPredicatesTest {

    @Test
    public void test_allPredicatesTrue_returnsTrue() {
        Person alice = TypicalPersons.ALICE;
        Predicate<Person> p1 = p ->
                p.getName().fullName.toLowerCase().contains("alice");
        Predicate<Person> p2 = p ->
                p.getEmail().value.toLowerCase().contains("@example.com");

        AllOfPersonPredicates all =
                new AllOfPersonPredicates(Arrays.asList(p1, p2));

        assertTrue(all.test(alice));
    }

    @Test
    public void test_onePredicateFalse_returnsFalse() {
        Person alice = TypicalPersons.ALICE;

        Predicate<Person> p1 = p ->
                p.getName().fullName.toLowerCase().contains("alice");
        Predicate<Person> p2 = p ->
                p.getAddress().value.toLowerCase().contains("clementi"); // false for ALICE

        AllOfPersonPredicates all =
                new AllOfPersonPredicates(Arrays.asList(p1, p2));

        assertFalse(all.test(alice));
    }

    @Test
    public void test_emptyList_returnsTrue() {
        AllOfPersonPredicates all = new AllOfPersonPredicates(Arrays.asList());
        assertTrue(all.test(TypicalPersons.ALICE));
    }

    @Test
    public void test_equals() {
        Predicate<Person> alwaysTrue = p -> true;
        Predicate<Person> alwaysFalse = p -> false;

        AllOfPersonPredicates a1 = new AllOfPersonPredicates(Arrays.asList(alwaysTrue));
        AllOfPersonPredicates a2 = new AllOfPersonPredicates(Arrays.asList(alwaysTrue));
        AllOfPersonPredicates a3 = new AllOfPersonPredicates(Arrays.asList(alwaysFalse));

        assertTrue(a1.equals(a2));
        assertFalse(a1.equals(a3));
        assertFalse(a1.equals(null));
        assertFalse(a1.equals("not a predicate"));
    }

    @Test
    public void test_allMustMatch() {
        Person alice = TypicalPersons.ALICE;
        Person benson = TypicalPersons.BENSON;

        FieldContainsKeywordsPredicate nameIsAlice = new FieldContainsKeywordsPredicate(
                p -> p.getName().fullName, List.of("Alice"), true);
        FieldContainsKeywordsPredicate addressHasJurong = new FieldContainsKeywordsPredicate(
                p -> p.getAddress().value, List.of("Jurong"), false);

        AllOfPersonPredicates and = new AllOfPersonPredicates(
                List.of(nameIsAlice, addressHasJurong));

        assertTrue(and.test(alice));
        assertFalse(and.test(benson));
    }
}
