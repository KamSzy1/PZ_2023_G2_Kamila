package controllers_pop_employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EditEmployeeController {

    @FXML
    private GridPane addEmployeeGrid;
    @FXML
    private TextField addressField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button generateButton;
    @FXML
    private TextField groupField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField placeField;
    @FXML
    private TextField positionField;
    @FXML
    private Button saveButton;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField tokenField;
    @FXML
    private Label wrongLabel;
    @FXML
    private TextField zipCodeField;

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } else if (source == saveButton) {

        }
    }
}
