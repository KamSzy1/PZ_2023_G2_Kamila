package database_classes;

public class TasksTable {
    private static int id_task;
    private static String title;
    private static String description;
    private static int status_id;
    private static int user_id;

    public static int getId_task() {
        return id_task;
    }

    public static void setId_task(int id_task) {
        TasksTable.id_task = id_task;
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

    public static int getStatus_id() {
        return status_id;
    }

    public static void setStatus_id(int status_id) {
        TasksTable.status_id = status_id;
    }

    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int user_id) {
        TasksTable.user_id = user_id;
    }

}


