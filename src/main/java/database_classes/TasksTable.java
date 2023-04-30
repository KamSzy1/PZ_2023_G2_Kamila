package database_classes;

public class TasksTable {
    private static int idTask;
    private static String title;
    private static String description;
    private static int statusId;
    private static int userId;

    public TasksTable(int idTask, String title, String description, int statusId, int userId) {
        this.idTask = idTask;
        this.title = title;
        this.description = description;
        this.statusId = statusId;
        this.userId = userId;
    }

    public TasksTable() {

    }

    public static int getIdTask() {
        return idTask;
    }

    public static void setIdTask(int idTask) {
        TasksTable.idTask = idTask;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        TasksTable.title = title;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        TasksTable.description = description;
    }

    public static int getStatusId() {
        return statusId;
    }

    public static void setStatusId(int statusId) {
        TasksTable.statusId = statusId;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        TasksTable.userId = userId;
    }

}


