package controllers_pop_task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditTaskControllerTest {

    @Test
    public void refBoolTest() {
        boolean notTrue = false;
        assertEquals(EditTaskController.refBool(), notTrue);
    }
}
