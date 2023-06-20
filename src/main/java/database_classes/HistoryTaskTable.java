package database_classes;

import java.util.Date;

/**
 * Klasa reprezentująca tabelę "HistoryTask" z bazy danych
 */
public class HistoryTaskTable {

    /**
     * ID
     */
    private int idTaskHistory;
    /**
     * Data początkowa
     */
    private Date startDate;
    /**
     * Data zakończenia
     */
    private Date endDate;
    /**
     * ID zadania
     */
    private int tasksId;
    /**
     * Planowana data zakończenia
     */
    private Date plannedEnd;

    /**
     * Metoda zwracająca numer zadania archiwalnego
     *
     * @return ID zadania archwialnego
     */
    public int getIdTaskHistory() {
        return idTaskHistory;
    }

    /**
     * Metoda zwracająca datę rozpoczęcia zadania
     *
     * @return data rozpoczęcia
     */
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    /**
     * Metoda zwracająca numer zadania
     *
     * @return ID zadania
     */
    public int getTasksId() {
        return tasksId;
    }

    /**
     * Metoda zwracająca planowaną datę zakończenia
     *
     * @return planowana data zakończenia
     */
    public Date getPlannedEnd() {
        return plannedEnd;
    }

    /**
     * Metoda ustawiająca numer zadania archiwalnego
     *
     * @param idTaskHistory ustawiony numer zadania archiwalnego
     */
    public void setIdTaskHistory(int idTaskHistory) {
        this.idTaskHistory = idTaskHistory;
    }

    /**
     * Metoda ustawiająca datę rozpoczęcia
     *
     * @param startDate wybrana data rozpoczęcia
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Metoda ustawiająca numer zadania
     *
     * @param tasksId ustawiony numer zadania
     */
    public void setTasksId(int tasksId) {
        this.tasksId = tasksId;
    }

    /**
     * Metoda ustawiająca planowaną datę zakończenia
     *
     * @param plannedEnd wybrana planowana data zakończenia
     */
    public void setPlannedEnd(Date plannedEnd) {
        this.plannedEnd = plannedEnd;
    }
}

