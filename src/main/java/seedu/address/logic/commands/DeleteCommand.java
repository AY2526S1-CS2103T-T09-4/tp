package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX[, INDEX,...] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1, 2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: \n%1$s";
    public static final String MESSAGE_DELETE_PEOPLE_SUCCESS = "Deleted People: \n%1$s";

    private final List<Index> targetIndices;

    /**
     * Creates a DeleteCommand to delete the indices stated in targetIndices.
     */
    public DeleteCommand(List<Index> targetIndices) {
        requireNonNull(targetIndices);
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : this.targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        List<Index> sortedIndices = new ArrayList<>(this.targetIndices);
        Collections.sort(sortedIndices, (a, b) -> b.getZeroBased() - a.getZeroBased());

        List<Person> deletedPersons = new ArrayList<>();
        for (Index idx : sortedIndices) {
            Person personToDelete = lastShownList.get(idx.getZeroBased());
            model.deletePerson(personToDelete);
            deletedPersons.add(personToDelete);
        }

        if (deletedPersons.size() == 1) {
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(deletedPersons.get(0))));
        } else {
            String names = deletedPersons.stream()
                    .map(Messages::format)
                    .collect(Collectors.joining(",\n"));
            return new CommandResult(String.format(MESSAGE_DELETE_PEOPLE_SUCCESS, names));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
