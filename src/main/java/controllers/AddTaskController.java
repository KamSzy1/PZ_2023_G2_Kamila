package controllers;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.TasksTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddTaskController {
    @FXML
    private DatePicker timePicker;
    @FXML
    private TextField titleField;
    @FXML
    private ListView<?> personView;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    TasksTable tasksTable = new TasksTable();


    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        } else if (source == addButton) {
            // Dodawanie nowych zadań
            tasksTable.setTitle(titleField.getText());
            tasksTable.setDescription(descriptionArea.getText());
            tasksTable.setStatusId(2);
            tasksTable.setUserId(1);
            DatabaseConnector.connect();
            QExecutor.executeQuery("insert into tasks (title, description, status_id, user_id) values ('"
                    + tasksTable.getTitle() + "','"
                    + tasksTable.getDescription() + "','"
                    + tasksTable.getStatusId() + "','"
                    + tasksTable.getUserId() + "')");
        }
    }

    void addTask() {
        titleField.getText();
        descriptionArea.getText();
        personView.getSelectionModel().getSelectedItem();  // Tutaj jest ListView prawdopodobnie do zmienienia, ale to już podmieni się jak ktoś za ten panel się weźmie
        timePicker.getValue(); // Tutaj jest timePicker
    }

    public void initialize() {
        timePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

}
