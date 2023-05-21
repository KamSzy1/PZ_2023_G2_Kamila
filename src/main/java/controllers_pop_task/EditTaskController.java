package controllers_pop_task;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.HistoryTaskTable;
import database_classes.TasksTable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditTaskController {
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
    private Button saveButton;

    private final TasksTable tasksTable = new TasksTable();
    private final HistoryTaskTable historyTaskTable = new HistoryTaskTable();
    private ObservableList<String> names;
    private ObservableList<String> statuses;
    private final LocalDate currentDate = LocalDate.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String formattedDate = currentDate.format(formatter);
    int idTask;

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
            closeWindow(cancelButton);

        } else if (source == saveButton) {
            closeWindow(saveButton);
        }
    }

    public void setData(String title, String description, String name, String surname, String status, String planned_end){
        titleField.setText(title);
        descriptionArea.setText(description);
    }

    //Zamykanie okienka
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void userList() {
        try {
            DatabaseConnector.connect();
            names = FXCollections.observableArrayList();
            names.add("Wybierz osobę");
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
            statuses.add("Wybierz status");
            ResultSet rs = QExecutor.executeSelect("Select * from statuses");
            while (rs.next()) {
                statuses.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statusView.setItems(statuses);
    }
}
