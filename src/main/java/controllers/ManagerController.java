package controllers;

import com.example.system.Main;
import com.example.system.StageChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ManagerController {

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
    private GridPane gridTasks;
    @FXML
    private GridPane gridEmployee;
    @FXML
    private GridPane gridRaport;
    @FXML
    private GridPane gridSettings;
    @FXML
    private Label textLabel;

    public void buttonsHandlerPane(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == tasksButton) {
            gridTasks.toFront();
            textLabel.setText("Zadania");
        } else if (source == employeeButton) {
            gridEmployee.toFront();
            textLabel.setText("Pracownicy");
        } else if (source == raportButton) {
            gridRaport.toFront();
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
