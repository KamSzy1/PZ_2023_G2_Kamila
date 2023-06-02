package controllers_pop_task;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.TasksTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
    @FXML
    private Label wrongLabel;

    public static boolean isRefreshed;
    public static boolean refBool() {
        return isRefreshed;
    }
    private ObservableList<String> names;
    private ObservableList<String> statuses;
    String oldTitle;
    String oldDescription;
    String oldName;
    String oldSurname;
    String oldStatus;
    String oldPlanned_end;
    TasksTable task = new TasksTable();

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
            updateData();
            closeWindow(saveButton);
            isRefreshed= true;
            System.out.println(task.getEditIdTask());
        }
    }

    public void setData(int id_task, String title, String description, String name, String surname, String status, String planned_end){
        titleField.setText(title);
        descriptionArea.setText(description);
        personView.setValue(name + " " + surname);
        statusView.setValue(status);
        timePicker.setValue(LocalDate.parse(planned_end));

        task.setEditIdTask(id_task);
        oldTitle = title;
        oldDescription = description;
        oldName = name;
        oldSurname = surname;
        oldStatus = status;
        oldPlanned_end = planned_end;
    }

    public void updateData(){
        String newTitle = titleField.getText();
        String newDescription = descriptionArea.getText();
        int newName = personView.getSelectionModel().getSelectedIndex();
        int newStatus = statusView.getSelectionModel().getSelectedIndex();
        LocalDate data = timePicker.getValue();
        try {
            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE tasks SET " +
                    " title = '"+ newTitle +  "' ," +
                    " description = '"+ newDescription +  "' ," +
                    " user_id = "+ newName + " ," +
                    " status_id = "+ newStatus + " WHERE " +
                    " id_task = " + task.getEditIdTask()
            );
            QExecutor.executeQuery("UPDATE tasks_history SET planned_end= '"+data+"' WHERE tasks_id="+ task.getEditIdTask());
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
