package database_classes;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class HistoryTaskTable {

    private int idTaskHistory;
    private Date startDate;
    private Date endDate;
    private int tasksId;
    private Date plannedEnd;

    public int getIdTaskHistory() {
        return idTaskHistory;
    }

    public void setIdTaskHistory(int idTaskHistory) {
        this.idTaskHistory = idTaskHistory;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTasksId() {
        return tasksId;
    }

    public void setTasksId(int tasksId) {
        this.tasksId = tasksId;
    }

    public Date getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(Date plannedEnd) {
        this.plannedEnd = plannedEnd;
    }
}

