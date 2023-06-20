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

    /**
     * Konstruktor pusty
     */
    public TasksTable() {
    }

    /**
     * Konstruktor klasy
     *
     * @param idTask      ID zadania
     * @param title       tytuł
     * @param description opis
     * @param statusId    ID statusu zadania
     * @param userId      ID użytkownika
     */
    public TasksTable(int idTask, String title, String description, int statusId, int userId) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
        this.userId = userId;
        this.editTaskButton = new Button();
    }

    /**
     * Metoda zwracająca ID zadania
     *
     * @return ID zadania
     */
    public int getIdTask() {
        return idTask;
    }

    /**
     * Metoda zwracająca tytuł zadania
     *
     * @return tytuł
     */
    public String getTitle() {
        return title;
    }

    /**
     * Metoda zwracająca opis zadania
     *
     * @return opis
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metoda zwracająca ID statusu zadania
     *
     * @return ID statusu
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * Metoda zwracająca ID przypisanego użytkownika do zadania
     *
     * @return ID użytkownika
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Metoda zwracająca nazwę statusu zadania
     *
     * @return nazwa statusu
     */
    public String getNameStatus() {
        return nameStatus;
    }

    /**
     * Metoda zwracająca planowaną datę zakończenia
     *
     * @return data zakończenia
     */
    public Date getData() {
        return data;
    }

    /**
     * Metoda zwracająca imie przypisanego użytkownika do zadania
     *
     * @return imie użytkownika
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * Metoda zwracająca przycisk
     *
     * @return Przycisk
     */
    public Button getEditTaskButton() {
        return editTaskButton;
    }

    /**
     * Metoda ustawiająca ID zadania
     *
     * @param idTask ustalone ID zadania
     */
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    /**
     * Metoda ustawiająca tytuł zadania
     *
     * @param title ustalony tytuł zadania
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Metoda ustawiająca opis zadania
     *
     * @param description ustalony opis zadania
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Metoda ustalająca ID statusu zadania
     *
     * @param statusId ustalone ID statusu
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /**
     * Metoda ustalająca ID użytkownika przypisanego do zadania
     *
     * @param userId ustalone ID użytkownika
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Metoda ustalająca nazwe statusu zadania
     *
     * @param nameStatus ustalona nazwa statusu zadania
     */
    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }

    /**
     * Metoda ustalająca date rozpoczęcia zadania
     *
     * @param data ustalona data rozpoczęcia
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Metoda ustalająca imie użytkownika
     *
     * @param nameUser ustalone imie użytkownika
     */
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    /**
     * Metoda tworząca przycisk edycji zadania
     *
     * @param editTaskButton przycisk edycji zadania
     */
    public void setEditTaskButton(Button editTaskButton) {
        this.editTaskButton = editTaskButton;
    }

    /**
     * Metoda zwracająca ID zadania przy edycji
     *
     * @return ID edytowanego zadania
     */
    public static int getEditIdTask() {
        return editIdTask;
    }

    /**
     * Metoda ustalająca ID edytowanego zadania
     *
     * @param editIdTask ustalone ID edytowanego zadania
     */
    public static void setEditIdTask(int editIdTask) {
        TasksTable.editIdTask = editIdTask;
    }

}


