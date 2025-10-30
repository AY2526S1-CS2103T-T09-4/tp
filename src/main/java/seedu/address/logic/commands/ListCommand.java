package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {
    /**
     * Category enum to represent the different categories of persons.
     */
    public enum Category {
        ALL("all", Model.PREDICATE_SHOW_ALL_PERSONS),
        STAFF("staff", Model.PREDICATE_SHOW_ALL_STAFFS),
        CUSTOMER("customer", Model.PREDICATE_SHOW_ALL_CUSTOMERS),
        SUPPLIER("supplier", Model.PREDICATE_SHOW_ALL_SUPPLIERS);

        private final String keyword;
        private final Predicate<Person> predicate;

        Category(String keyword, Predicate<Person> predicate) {
            this.keyword = keyword;
            this.predicate = predicate;
        }

        private Predicate<Person> getPredicate() {
            return predicate;
        }

        /**
         * Returns the Category corresponding to the given input string.
         * The comparison is case-insensitive and ignores leading/trailing whitespace.
         * If no matching category is found, returns null.
         *
         * @param input the input string to match against category keywords
         * @return the matching Category or null if no match is found
         */
        public static Category fromString(String input) {
            String trimmedToLowerCaseInput = input.trim().toLowerCase();
            for (Category category : values()) {
                if (category.keyword.equals(trimmedToLowerCaseInput)) {
                    return category;
                }
            }

            return null;
        }
    }

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a list of all contacts or filters the list by the specified contact type.\n\n"
            + "**Format:**\n"
            + COMMAND_WORD + " <"
            + "all" + " | "
            + "staff" + " | "
            + "customer" + " | "
            + "supplier" + ">\n\n"
            + "**Examples:**\n"
            + COMMAND_WORD + " all\n"
            + COMMAND_WORD + " staff\n"
            + COMMAND_WORD + " customer";
    public static final String MESSAGE_SUCCESS_FORMAT = "Listed %s";

    private final Category category;

    /**
     * Creates a ListCommand to list all persons in the specified category.
     *
     * @param category the category of persons to list
     */
    public ListCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(category.getPredicate());
        return new CommandResult(String.format(MESSAGE_SUCCESS_FORMAT, this.category.toString().toLowerCase()));
    }
}
