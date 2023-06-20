package controllers_pop_employee;

import controllers_pop_task.EditTaskController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EdtEmployeeControllerTest {

    @Test
    public void refBoolTest() {
        boolean notTrue = false;
        assertEquals(EditTaskController.refBool(), notTrue);
    }
}
