package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;

public class RemarkCommandTest {
    private static final String MESSAGE_NOT_IMPLEMENTED_YET = "Message is not implemented yet";

    @Test
    public void execute() {
        Model model = null;
        assertCommandFailure(new RemarkCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }

}
