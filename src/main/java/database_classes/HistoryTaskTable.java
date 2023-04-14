package database_classes;

import java.util.Calendar;

public class HistoryTaskTable {

    private static int id_task_history;
    private static Calendar start_date;
    private static Calendar end_date;
    private static int tasks_id;
    private static Calendar planned_end;

    public static int getId_task_history() {
        return id_task_history;
    }

    public static void setId_task_history(int id_task_history) {
        HistoryTaskTable.id_task_history = id_task_history;
    }

    public static Calendar getStart_date() {
        return start_date;
    }

    public static void setStart_date(Calendar start_date) {
        HistoryTaskTable.start_date = start_date;
    }

    public static Calendar getEnd_date() {
        return end_date;
    }

    public static void setEnd_date(Calendar end_date) {
        HistoryTaskTable.end_date = end_date;
    }

    public static int getTasks_id() {
        return tasks_id;
    }

    public static void setTasks_id(int tasks_id) {
        HistoryTaskTable.tasks_id = tasks_id;
    }

    public static Calendar getPlanned_end() {
        return planned_end;
    }

    public static void setPlanned_end(Calendar planned_end) {
        HistoryTaskTable.planned_end = planned_end;
    }
}

