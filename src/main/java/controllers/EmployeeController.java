package controllers;


import com.example.system.Main;
import com.example.system.StageChanger;
import database.DatabaseConnector;
import database.QExecutor;
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
import java.util.Objects;

public class EmployeeController {

    @FXML
    private Button myTasksButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button mailEditButton;
    @FXML
    private Button passwordEditButton;
    @FXML
    private GridPane gridMyTasks;
    @FXML
    private GridPane gridReport;
    @FXML
    private GridPane gridSettings;
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
    private Label managerLabel;
    @FXML
    private TableColumn<?, ?> myTaskDescription;
    @FXML
    private TableColumn<?, ?> myTaskEdit;
    @FXML
    private TableColumn<?, ?> myTaskID;
    @FXML
    private TableColumn<?, ?> myTaskPlannedDate;
    @FXML
    private TableColumn<?, ?> myTaskStatus;
    @FXML
    private TableColumn<?, ?> myTaskTitle;
    @FXML
    private TableView<TasksTable> myTaskTableView;
    UsersTable usersTable = new UsersTable();

    @FXML
    private void initialize() {
        welcomeLabel.setText("Witaj " + usersTable.getName() + " " + usersTable.getSurname() + "!");
        task();
    }

    private ObservableList<TasksTable> taskTable;

    //To jest do obsługi wszystkich buttonów, które zmieniają tylko grid
    public void buttonsHandlerPane(ActionEvent event) throws IOException {
        Object source = event.getSource();
        /*DatabaseConnector.connect();

        try{
            ResultSet result = QExecutor.executeSelect("SELECT * FROM users WHERE id_user =" + MainController.id_user);
            result.next();
            System.out.println(result.getString("name"));
            //welcomeLabel.setText("Witaj" + result.getString("name") + " " + result.getString("surname"));
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }*/

        if (source == myTasksButton) {
            gridMyTasks.toFront();
            textLabel.setText("Moje zadania");

        } else if (source == reportButton) {
            gridReport.toFront();
            textLabel.setText("Generowanie raportów");
        } else if (source == settingsButton) {
            gridSettings.toFront();
            textLabel.setText("Ustawienia");
        }
    }

    //Wyświetlanie "Moich Zadań"
    public void task() {
        DatabaseConnector.connect();
        try {
            taskTable = FXCollections.observableArrayList();

            ResultSet result = QExecutor.executeSelect("SELECT * FROM tasks INNER JOIN statuses ON tasks.status_id = statuses.id_status WHERE user_id = " + usersTable.getIdUser());

            while (result.next()) {
                TasksTable task = new TasksTable();

                task.setIdTask(result.getInt("id_task"));
                task.setTitle(result.getString("title"));
                task.setDescription(result.getString("description"));
                task.setName(result.getString("name"));
                taskTable.add(task);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        myTaskID.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        myTaskTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        myTaskDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        myTaskStatus.setCellValueFactory(new PropertyValueFactory<>("name"));
        myTaskTableView.setItems(taskTable);

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
        } else if (source == mailEditButton) {
            System.out.println("A");
        } else if (source == passwordEditButton) {
            System.out.println("B");
        }
    }


}
