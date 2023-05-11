package controllers_popup;

        import database.DatabaseConnector;
        import database.QExecutor;
        import database_classes.HistoryTaskTable;
        import database_classes.TasksTable;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.stage.Stage;
        import java.io.IOException;
        import java.sql.Date;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;

public class AddTaskController {
    @FXML
    private DatePicker timePicker;
    @FXML
    private TextField titleField;
    @FXML
    private ListView<String> personView;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;

    private ListView<String> list;
    TasksTable tasksTable = new TasksTable();
    HistoryTaskTable historyTaskTable = new HistoryTaskTable();
    private ObservableList<String> names;
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);

    @FXML
    public void initialize() {
        userList();
        timePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        } else if (source == addButton) {
            addTask();
        }
    }

    public void addTask() {
        // Dodawanie nowych zadań
        LocalDate date = timePicker.getValue();
        tasksTable.setTitle(titleField.getText());
        tasksTable.setDescription(descriptionArea.getText());
        historyTaskTable.setPlannedEnd(Date.valueOf(date));
        historyTaskTable.setStartDate(Date.valueOf(formattedDate));
        tasksTable.setStatusId(2);
        tasksTable.setUserId(Integer.parseInt(personView.getSelectionModel().getSelectedItem()));
        System.out.println(timePicker);

        try {
            DatabaseConnector.connect();
            QExecutor.executeQuery("insert into tasks (title, description, status_id, user_id) values ('"
                    + tasksTable.getTitle() + "','"
                    + tasksTable.getDescription() + "','"
                    + tasksTable.getStatusId() + "','"
                    + tasksTable.getUserId() + "')");

            QExecutor.executeQuery("insert into tasks_history (planned_end, start_date, tasks_id) values ('"
                    + historyTaskTable.getPlannedEnd() + "','"
                    + historyTaskTable.getStartDate() + "',"
                    + "(SELECT MAX(id_task) FROM tasks))");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void userList() {
        try {
            DatabaseConnector.connect();
            names = FXCollections.observableArrayList();
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM users");
            while (rs.next()) {
                names.add(rs.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        personView.setItems(names);
    }

}