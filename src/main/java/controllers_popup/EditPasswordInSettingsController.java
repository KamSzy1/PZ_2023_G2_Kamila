package controllers_popup;

import database.DatabaseConnector;
import database.QExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import other.PasswordHash;
import other.ValidateData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPasswordInSettingsController {

    @FXML
    private Button cancel2Button;

    @FXML
    private Button cancelButton;
    @FXML
    private Button continueButton;
    @FXML
    private GridPane passwordGrid;
    @FXML
    private TextField passwordActualField;
    @FXML
    private TextField passwordNewField;
    @FXML
    private TextField passwordRepeatField;
    @FXML
    private Button saveButton;
    @FXML
    private TextField tokenField;
    @FXML
    private GridPane tokenGrid;
    @FXML
    private Label wrongLabel;

    @FXML
    public void initialize() {
        tokenGrid.toFront();
    }

    //To jest do obsługi buttonów
    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == continueButton) {
            //Sprawdzenie tokenu
            try {
                ValidateData.goodToken(tokenField.getText());
            } catch (Exception e) {
                wrongLabel.setText(e.getMessage());
            }

            passwordGrid.toFront();

        } else if (source == cancel2Button) {
            //Zamykanie okienka
            Stage stage = (Stage) cancel2Button.getScene().getWindow();
            stage.close();
        } else if (source == saveButton) {
            //Zmiana hasła
            passwordActualField.getText();
            passwordNewField.getText();
            passwordRepeatField.getText();

            try {
                ValidateData.samePassword(passwordNewField.getText(),passwordRepeatField.getText());

                String hashedPassword = PasswordHash.hashedPassword(passwordNewField.getText());
                DatabaseConnector.connect();
                QExecutor.executeQuery("UPDATE login SET password='"+hashedPassword +
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
