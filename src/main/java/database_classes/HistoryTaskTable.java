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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getTasksId() {
        return tasksId;
    }

    public Date getPlannedEnd() {
        return plannedEnd;
    }

    public void setIdTaskHistory(int idTaskHistory) {
        this.idTaskHistory = idTaskHistory;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTasksId(int tasksId) {
        this.tasksId = tasksId;
    }

    public void setPlannedEnd(Date plannedEnd) {
        this.plannedEnd = plannedEnd;
    }
}

