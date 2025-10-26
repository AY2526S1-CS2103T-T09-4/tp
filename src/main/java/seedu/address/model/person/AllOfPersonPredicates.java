package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person} satisfies all of the supplied predicates.
 * Combines multiple {@link java.util.function.Predicate} with logical AND.
 */

public class AllOfPersonPredicates implements Predicate<Person> {
    private final List<Predicate<Person>> predicates;

    public AllOfPersonPredicates(List<Predicate<Person>> predicates) {
        this.predicates = Objects.requireNonNull(predicates);
    }

    @Override
    public boolean test(Person p) {
        return predicates.stream().allMatch(pred -> pred.test(p));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AllOfPersonPredicates o && predicates.equals(o.predicates));
    }

    @Override
    public int hashCode() {
        return predicates.hashCode();
    }
}
