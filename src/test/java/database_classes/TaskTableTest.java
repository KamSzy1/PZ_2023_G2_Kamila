package database_classes;

import javafx.scene.control.Button;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskTableTest {

    @Test
    public void TaskTableTest() {
        int idTask = 1;
        String title = "Zadanie 10";
        String description = "Jakiś opisik";
        int statusId = 2;
        int userId = 3;
        Button editTaskButton;
        TasksTable tasksTable = new TasksTable();

        tasksTable.setIdTask(idTask);
        tasksTable.setTitle(title);
        tasksTable.setDescription(description);
        tasksTable.setStatusId(statusId);
        tasksTable.setUserId(userId);

        int actualIdTask = tasksTable.getIdTask();
        String actualTitle = tasksTable.getTitle();
        String actualDescription = tasksTable.getDescription();
        int actualStatusId = tasksTable.getStatusId();
        int actualUserId = tasksTable.getUserId();

        editTaskButton = tasksTable.getEditTaskButton();
        assertNull(editTaskButton);

        assertEquals(idTask, actualIdTask);
        assertEquals(title, actualTitle);
        assertEquals(description, actualDescription);
        assertEquals(statusId, actualStatusId);
        assertEquals(userId, actualUserId);
    }
}
