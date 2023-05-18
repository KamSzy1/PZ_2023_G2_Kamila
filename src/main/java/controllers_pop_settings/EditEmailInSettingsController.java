package controllers_pop_settings;

import database.DatabaseConnector;
import database.QExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import validate.ValidateEmployee;

public class EditEmailInSettingsController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button cancel2Button;
    @FXML
    private Button continueButton;
    @FXML
    private Button saveButton;
    @FXML
    private GridPane emailGrid;
    @FXML
    private GridPane tokenGrid;
    @FXML
    private TextField emailActualField;
    @FXML
    private TextField emailNewField;
    @FXML
    private TextField emailRepeatField;
    @FXML
    private TextField tokenField;
//    @FXML
//    private Label wrongLabel;
    @FXML
    private Label wrongTokenLabel;

    @FXML
    public void initialize() {
        tokenGrid.toFront();
    }

    //To jest do obsługi buttonów
    public void buttonsHandler(ActionEvent event) {
        Object source = event.getSource();

        if (source == continueButton) {
            //Sprawdzenie tokenu
            try {
                ValidateEmployee.goodToken(tokenField.getText());
            } catch (Exception e) {
                wrongTokenLabel.setText(e.getMessage());
            }

            emailGrid.toFront();

        } else if (source == cancel2Button) {
            //Zamykanie okienka
            Stage stage = (Stage) cancel2Button.getScene().getWindow();
            stage.close();
        } else if (source == saveButton) {
            //Zmiana adresu Email
            emailActualField.getText();
            emailNewField.getText();
            emailRepeatField.getText();

            try {
                ValidateEmployee.goodEmail(emailActualField.getText());
                ValidateEmployee.goodEmail(emailNewField.getText());
                ValidateEmployee.goodEmail(emailRepeatField.getText());
                ValidateEmployee.goodAddress(emailActualField.getText());
                ValidateEmployee.goodAddress(emailNewField.getText());
                ValidateEmployee.goodAddress(emailRepeatField.getText());

                DatabaseConnector.connect();
                QExecutor.executeQuery("UPDATE login SET email='"+emailNewField.getText() +
                        "' WHERE token='"+ tokenField.getText()+"'");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        }

    }
}
