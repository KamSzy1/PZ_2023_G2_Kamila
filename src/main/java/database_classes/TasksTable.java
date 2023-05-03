package database_classes;

public class TasksTable {
    private int idTask;
    private String title;
    private String description;
    private int statusId;
    private int userId;
    private String nameStatus;

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    private String nameUser;

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String name) {
        this.nameStatus = name;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TasksTable(int idTask, String title, String description, int statusId, int userId) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
        this.userId = userId;
    }

    public TasksTable() {

    }

}


