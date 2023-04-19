package controllers;


import com.example.system.Main;
import com.example.system.StageChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EmployeeController {

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
    private GridPane gridRaport;
    @FXML
    private GridPane gridSettings;
    @FXML
    private Label textLabel;


    public void buttonsHandlerPane(ActionEvent event) throws IOException {
        if (event.getSource() == tasksButton) {
            gridTasks.toFront();
            textLabel.setText("Zadania");
        }

        if (event.getSource() == raportButton) {
            gridRaport.toFront();
            textLabel.setText("Generowanie raportów");
        }

        if (event.getSource() == settingsButton) {
            gridSettings.toFront();
            textLabel.setText("Ustawienia");
        }

        if (event.getSource() == logoutButton) {
            StageChanger stageChanger = new StageChanger();
            stageChanger.changeSize(915, 630);
            stageChanger.changeScene("/main.fxml");
        }

    }


}
