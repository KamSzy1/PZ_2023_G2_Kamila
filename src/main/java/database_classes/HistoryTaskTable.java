package database_classes;

import java.util.Calendar;

public class HistoryTaskTable {

    private int idTaskHistory;
    private Calendar startDate;
    private Calendar endDate;
    private int tasksId;
    private Calendar plannedEnd;

    public int getIdTaskHistory() {
        return idTaskHistory;
    }

    public void setIdTaskHistory(int idTaskHistory) {
        this.idTaskHistory = idTaskHistory;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getTasksId() {
        return tasksId;
    }

    public void setTasksId(int tasksId) {
        this.tasksId = tasksId;
    }

    public Calendar getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(Calendar plannedEnd) {
        this.plannedEnd = plannedEnd;
    }
}

