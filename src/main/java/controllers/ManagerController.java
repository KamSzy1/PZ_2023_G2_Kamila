package controllers;

import com.example.system.Main;
import com.example.system.StageChanger;
import database.DatabaseConnector;
import database.QExecutor;
import database_classes.StatusesTable;
import database_classes.TasksTable;
import database_classes.UsersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class ManagerController {

    @FXML
    private Button myTasksButton;
    @FXML
    private Button tasksButton;
    @FXML
    private Button employeeButton;
    @FXML
    private Button raportButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button addTaskButton;
    @FXML
    private Button mailEditButton;
    @FXML
    private Button passwordEditButton;
    @FXML
    private GridPane gridTasks;
    @FXML
    private GridPane gridEmployee;
    @FXML
    private GridPane gridRaport;
    @FXML
    private GridPane gridSettings;
    @FXML
    private GridPane gridMyTasks;
    @FXML
    private Label textLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label zipLabel;
    @FXML
    private Label placeLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label groupLabel;
    @FXML
    private TableColumn<?, ?> myTaskDescription;
    @FXML
    private TableColumn<?, ?> myTaskEdit;
    @FXML
    private TableColumn<TasksTable, Integer> myTaskID;
    @FXML
    private TableColumn<?, ?> myTaskPlannedDate;
    @FXML
    private TableColumn<?, ?> myTaskStatus;
    @FXML
    private TableColumn<?, ?> myTaskTitle;
    @FXML
    private TableView<TasksTable> myTaskTableView;
    @FXML
    private TableView<TasksTable> taskTableView;
    @FXML
    private TableColumn<TasksTable, String> taskDescription;
    @FXML
    private TableColumn<TasksTable, ?> taskEdit;
    @FXML
    private TableColumn<TasksTable, Integer> taskEmployee;
    @FXML
    private TableColumn<TasksTable, Integer> taskID;
    @FXML
    private TableColumn<TasksTable, ?> taskPlannedDate;
    @FXML
    private TableColumn<TasksTable, Integer> taskStatus;
    @FXML
    private TableColumn<TasksTable, String> taskTitle;
    private ObservableList<TasksTable> myTaskTable;
    private ObservableList<TasksTable> taskTable;

    UsersTable usersTable = new UsersTable();

    @FXML
    private void initialize() {
        welcomeLabel.setText("Witaj " + usersTable.getName() + " " + usersTable.getSurname() + "!");
    }

    public void myTask() {
        DatabaseConnector.connect();
        try {
            myTaskTable = FXCollections.observableArrayList();

            ResultSet result = QExecutor.executeSelect("SELECT * FROM tasks INNER JOIN statuses ON tasks.status_id = statuses.id_status WHERE user_id = " + usersTable.getIdUser());

            while (result.next()) {
                TasksTable task = new TasksTable();

                task.setIdTask(result.getInt("id_task"));
                task.setTitle(result.getString("title"));
                task.setDescription(result.getString("description"));
                task.setName(result.getString("name"));
                myTaskTable.add(task);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        myTaskID.setCellValueFactory(new PropertyValueFactory<TasksTable, Integer>("idTask"));
        myTaskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        myTaskDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        myTaskStatus.setCellValueFactory(new PropertyValueFactory<>("name"));
        myTaskTableView.setItems(taskTable);

    }

    //Wyświetlanie zadań
    public void tasks() {
        DatabaseConnector.connect();
        try {
            taskTable = FXCollections.observableArrayList();

            ResultSet result = QExecutor.executeSelect("SELECT * FROM tasks INNER JOIN statuses ON tasks.status_id = statuses.id_status");

            while (result.next()) {
                TasksTable task = new TasksTable();

                task.setIdTask(result.getInt("id_task"));
                task.setTitle(result.getString("title"));
                task.setDescription(result.getString("description"));
                task.setStatusId(result.getInt("status_id"));
                task.setUserId(result.getInt("user_id"));
                taskTable.add(task);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        taskID.setCellValueFactory(new PropertyValueFactory<TasksTable, Integer>("idTask"));
        taskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("statusId"));
        taskEmployee.setCellValueFactory(new PropertyValueFactory<>("userId"));
        taskTableView.setItems(taskTable);
    }

    //To jest do obsługi wszystkich buttonów, które zmieniają tylko grid
    public void buttonsHandlerPane(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == myTasksButton) {
            gridMyTasks.toFront();
            textLabel.setText("Moje zadania");
            myTask();
        } else if (source == tasksButton) {
            gridTasks.toFront();
            textLabel.setText("Zadania");
            tasks();
        } else if (source == employeeButton) {
            gridEmployee.toFront();
            textLabel.setText("Pracownicy");
        } else if (source == raportButton) {
            gridRaport.toFront();
            textLabel.setText("Generowanie raportów");
        } else if (source == settingsButton) {
            gridSettings.toFront();
            textLabel.setText("Ustawienia");
        }

    }

    //To jest do obsługi wszystkich buttonów, które zmieniają cały panel (Stage) i PopupWindow
    public void buttonsHandlerStages(ActionEvent event) throws IOException {
        Object source = event.getSource();
        Stage stage;
        Parent root;
        StageChanger stageChanger = new StageChanger();

        if (source == logoutButton) {
            stageChanger.changeSize(915, 630);
            stageChanger.changeScene("/main.fxml");
        } else if (source == addTaskButton) {
            stage = new Stage();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/addTask.fxml")));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(addTaskButton.getScene().getWindow());
            stage.showAndWait();
        } else if (source == mailEditButton) {
            System.out.println("A");
        } else if (source == passwordEditButton) {
            System.out.println("B");
        }
    }
}
