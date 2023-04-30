package controllers;


import com.example.system.Main;
import com.example.system.StageChanger;
import database.DatabaseConnector;
import database.QExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        } else if (source == logoutButton) {
            StageChanger stageChanger = new StageChanger();
            stageChanger.changeSize(915, 630);
            stageChanger.changeScene("/main.fxml");
        }

    }


}
