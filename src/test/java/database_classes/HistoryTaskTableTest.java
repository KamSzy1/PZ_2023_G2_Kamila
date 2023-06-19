package database_classes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryTaskTableTest {

    HistoryTaskTable historyTaskTable = new HistoryTaskTable();

    @Test
    public void testGetIdTaskHistory() {
        final int expectedIdTaskHistory = 1;
        historyTaskTable.setIdTaskHistory(expectedIdTaskHistory);
        int actualIdTaskHistory = historyTaskTable.getIdTaskHistory();
        assertEquals(expectedIdTaskHistory, actualIdTaskHistory);
    }

    @Test
    void testGetTasksId() {
        int expectedTasksId = 5;
        historyTaskTable.setTasksId(expectedTasksId);
        int actualTasksId = historyTaskTable.getTasksId();
        assertEquals(expectedTasksId, actualTasksId);
    }

    @Test
    void testSetIdTaskHistory() {
        int idTaskHistory = 1;
        historyTaskTable.setIdTaskHistory(idTaskHistory);
        int actualIdTaskHistory = historyTaskTable.getIdTaskHistory();
        assertEquals(idTaskHistory, actualIdTaskHistory);
    }

    @Test
    void testSetTasksId() {
        int tasksId = 2;
        historyTaskTable.setTasksId(tasksId);
        int actualTasksId = historyTaskTable.getTasksId();
        assertEquals(tasksId, actualTasksId);
    }


}
