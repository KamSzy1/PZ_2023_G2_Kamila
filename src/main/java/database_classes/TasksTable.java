package database_classes;

import javafx.scene.control.Button;

import java.sql.Date;

/**
 * Klasa reprezentująca tabelę "Tasks" z bazy danych
 */
public class TasksTable {

    /**
     * Numer do przycisku edycji
     */
    private static int editIdTask;
    /**
     * ID
     */
    private int idTask;
    /**
     * Tytuł
     */
    private String title;
    /**
     * Opis
     */
    private String description;
    /**
     * ID statusu
     */
    private int statusId;
    /**
     * ID użytkownika
     */
    private int userId;
    /**
     * Nazwa stanowiska
     */
    private String nameStatus;
    /**
     * Data
     */
    private Date data;
    /**
     * Imię użytkownika
     */
    private String nameUser;
    /**
     * Przycisk edycji
     */
    private Button editTaskButton;

    public TasksTable() {}

    public TasksTable(int idTask, String title, String description, int statusId, int userId) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
        this.userId = userId;
        this.editTaskButton = new Button();
    }

    public int getIdTask() {
        return idTask;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getUserId() {
        return userId;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public Date getData() {
        return data;
    }

    public String getNameUser() {
        return nameUser;
    }

    public Button getEditTaskButton() {
        return editTaskButton;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setEditTaskButton(Button editTaskButton) {
        this.editTaskButton = editTaskButton;
    }

    public static int getEditIdTask() {
        return editIdTask;
    }

    public static void setEditIdTask(int editIdTask) {
        TasksTable.editIdTask = editIdTask;
    }

}


