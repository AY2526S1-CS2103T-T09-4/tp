package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEMS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHIFTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose fields match the given predicate
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds persons by specified fields (case-insensitive) using keywords.\n"
            + "Without prefixes, searches by name.\n\n"
            + "**Search Rules:**\n"
            + "    Case-Insensitive: e.g., 'hans' will match 'Hans'.\n"
            + "    Full Word Match: Only full words will be matched. e.g., 'Han' will NOT match 'Hans'.\n"
            + "    Field Search: Only the field specified by the prefix is searched.\n"
            + "    Keyword Order: The order of keywords does NOT matter. e.g., 'Hans Bo' will match 'Bo Hans'.\n"
            + "    Multiple Keywords (Per Field): Persons matching at least ONE keyword in a field will be returned "
            + "(OR search). e.g., 'find John Doe' returns John Gruber, Bo Doe.\n"
            + "    Multiple Prefixes (Across Fields): Persons must match ALL specified prefixed fields "
            + "(AND search). e.g., 'find n/John p/91234567' returns John with that phone number.\n\n"
            + "**Format:**\n"
            + COMMAND_WORD + " "
            + "[" + PREFIX_NAME + "NAME_KEYWORD [MORE_KEYWORDS]] "
            + "[" + PREFIX_PHONE + "PHONE_KEYWORD [MORE_KEYWORDS]] "
            + "[" + PREFIX_EMAIL + "EMAIL_KEYWORD [MORE_KEYWORDS]] "
            + "[" + PREFIX_ADDRESS + "ADDRESS_KEYWORD [MORE_KEYWORDS]] "
            + "[" + PREFIX_TAG + "TAG_KEYWORD [MORE_KEYWORDS]] "
            + "[" + PREFIX_SHIFTS + "DATE_KEYWORD [MORE_KEYWORDS]] "
            + "[" + PREFIX_ITEMS + "ITEM_KEYWORD [MORE_KEYWORDS]] "
            + "[" + PREFIX_DAYS + "DAY_KEYWORD [MORE_KEYWORDS]]\n\n"
            + "**Examples:**\n"
            + COMMAND_WORD + " John Doe\n"
            + COMMAND_WORD + " " + PREFIX_NAME + "John " + PREFIX_SHIFTS + "2025-10-10\n"
            + COMMAND_WORD + " " + PREFIX_EMAIL + "example.com " + PREFIX_TAG + "friend";

    private final Predicate<Person> predicate;

    public FindCommand(NameContainsKeywordsPredicate namePredicate) {
        this((Predicate<Person>) namePredicate);
    }

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = requireNonNull(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
