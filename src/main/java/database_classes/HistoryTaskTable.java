package database_classes;

import java.util.Calendar;

public class HistoryTaskTable {

    private static int idTaskHistory;
    private static Calendar startDate;
    private static Calendar endDate;
    private static int tasksId;
    private static Calendar plannedEnd;

    public static int getIdTaskHistory() {
        return idTaskHistory;
    }

    public static void setIdTaskHistory(int idTaskHistory) {
        HistoryTaskTable.idTaskHistory = idTaskHistory;
    }

    public static Calendar getStartDate() {
        return startDate;
    }

    public static void setStartDate(Calendar startDate) {
        HistoryTaskTable.startDate = startDate;
    }

    public static Calendar getEndDate() {
        return endDate;
    }

    public static void setEndDate(Calendar endDate) {
        HistoryTaskTable.endDate = endDate;
    }

    public static int getTasksId() {
        return tasksId;
    }

    public static void setTasksId(int tasksId) {
        HistoryTaskTable.tasksId = tasksId;
    }

    public static Calendar getPlannedEnd() {
        return plannedEnd;
    }

    public static void setPlannedEnd(Calendar plannedEnd) {
        HistoryTaskTable.plannedEnd = plannedEnd;
    }
}

