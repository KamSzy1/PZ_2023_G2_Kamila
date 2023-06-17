package database_classes;

import java.util.Date;

/**
 * Klasa reprezentująca tabelę "HistoryTask" z bazy danych
 */
public class HistoryTaskTable {

    /**
     * @param idTaskHistory ID
     */
    private int idTaskHistory;
    /**
     * @param startDate Data początkowa
     */
    private Date startDate;
    /**
     * @param endDate Data zakończenia
     */
    private Date endDate;
    /**
     * @param tasksId ID zadania
     */
    private int tasksId;
    /**
     * @param plannedEnd Planowana data zakończenia
     */
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

