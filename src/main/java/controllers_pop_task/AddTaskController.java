package controllers_pop_task;

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
import validate.ValidateTask;

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
    private ComboBox<String> personView;
    @FXML
    private ComboBox<String> statusView;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private Label wrongLabel;

    private final TasksTable tasksTable = new TasksTable();
    private final HistoryTaskTable historyTaskTable = new HistoryTaskTable();
    private ObservableList<String> names;
    private ObservableList<String> statuses;
    private final LocalDate currentDate = LocalDate.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String formattedDate = currentDate.format(formatter);
    public static boolean isRefreshed;
    public static boolean refBool() {
        return isRefreshed;
    }
    private LocalDate date;


    @FXML
    public void initialize() {
        userList();
        statusList();
        timePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.isBefore(today));
            }
        });

        timePicker.getEditor().setDisable(true);
    }

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        } else if (source == addButton) {
            addTask();
            isRefreshed = true;
        }
    }

    public void addTask() {
        // Dodawanie nowych zadań
        try {
            tryGetData();
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

            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            wrongLabel.setText("Wybierz datę zakończenia");
        } catch (RuntimeException e){
            wrongLabel.setText("Uzupełnij wszystkie pola");
        } catch(Exception e){
            wrongLabel.setText(e.getMessage());
        }
    }

    public void userList() {
        try {
            DatabaseConnector.connect();
            names = FXCollections.observableArrayList();
            ResultSet rs = QExecutor.executeSelect("SELECT * FROM users");
            while (rs.next()) {
                names.addAll(rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        personView.setItems(names);
    }

    public void statusList() {
        try {
            DatabaseConnector.connect();
            statuses = FXCollections.observableArrayList();
            ResultSet rs = QExecutor.executeSelect("Select * from statuses");
            while (rs.next()) {
                statuses.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statusView.setItems(statuses);
    }

    private void tryGetData() throws Exception{ //Do tego trzeba dodać walidację, czy data jest poprawna + dodać wybiranie stanowiska praocwnika
        String title = titleField.getText();
        String description = descriptionArea.getText();

        ValidateTask.checkTitle(title);
        ValidateTask.checkDescription(description);

        date = timePicker.getValue();
        tasksTable.setTitle(title);
        tasksTable.setDescription(description);
        historyTaskTable.setPlannedEnd(Date.valueOf(date));
        historyTaskTable.setStartDate(Date.valueOf(formattedDate));
        tasksTable.setStatusId(statusView.getSelectionModel().getSelectedIndex()+1);
        tasksTable.setUserId(personView.getSelectionModel().getSelectedIndex()+1);
    }
}