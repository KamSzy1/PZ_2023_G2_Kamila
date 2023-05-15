package controllers_pop_task;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.HistoryTaskTable;
import database_classes.TasksTable;
import javafx.beans.property.SimpleIntegerProperty;
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
    private ListView<String> personView;
    @FXML
    private ListView<String> statusView;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    private final TasksTable tasksTable = new TasksTable();
    private final HistoryTaskTable historyTaskTable = new HistoryTaskTable();
    private ObservableList<String> names;
    private final LocalDate currentDate = LocalDate.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String formattedDate = currentDate.format(formatter);
    private int idTask;

    @FXML
    public void initialize() {
        try {
            DatabaseConnector.connect();
//SELECT t.title, t.description, u.name, u.surname, s.name, tk.planned_end FROM tasks AS t JOIN statuses AS s ON t.status_id = s.id_status JOIN users AS u ON t.user_id=u.id_user JOIN tasks_history AS tk ON tk.tasks_id=t.id_task WHERE t.id_task = 8
            ResultSet result = QExecutor.executeSelect("SELECT t.title, t.description, u.name, u.surname, s.name, tk.planned_end FROM tasks AS t " +
                    "JOIN statuses AS s ON t.status_id = s.id_status " +
                    "JOIN users AS u ON t.user_id=u.id_user " +
                    "JOIN tasks_history AS tk ON tk.tasks_id=t.id_task " +
                    "WHERE t.id_task = " + idTask);
            result.next();
            System.out.println(idTask);

            titleField.setText(result.getString("title"));
            descriptionArea.setText(result.getString("description"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == cancelButton) {
            closeWindow(cancelButton);

        } else if (source == saveButton) {
            closeWindow(saveButton);
        }
    }

    public void setIdTask(int idTask){
        this.idTask = idTask;
    }

    //Zamykanie okienka
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
