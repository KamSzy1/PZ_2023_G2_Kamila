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

import java.sql.ResultSet;
import java.sql.SQLException;

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

    String token;

    @FXML
    public void initialize() {
        tokenGrid.toFront();
    }

    //To jest do obsługi buttonów
    public void buttonsHandler(ActionEvent event) {
        Object source = event.getSource();

        if (source == continueButton) {
            checkIfTokenIsEmpty();
        } else if (source == saveButton) {
            changeEmail();
        } else if (source == cancelButton) {
            closeWindow(cancelButton);
        } else if (source == cancel2Button) {
            closeWindow(cancel2Button);
        }
    }

    private void checkIfTokenIsEmpty() {
        token = tokenField.getText();

        if (token.isEmpty()) {
            wrongTokenLabel.setText("Pusty token!");
        } else {
            checkToken();
        }
    }

    //Sprawdzenie tokenu
    private void checkToken() {
        try {
            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT * FROM login WHERE token ='" + token + "'");
            result.next();
            emailGrid.toFront();

        } catch (SQLException e) {
            e.printStackTrace();
            wrongTokenLabel.setText("Błędny token");
        }
    }

    //Zmiana adresu Email
    private void changeEmail() {
        try {
            ValidateEmployee.goodEmail(emailActualField.getText());
            ValidateEmployee.goodEmail(emailNewField.getText());
            ValidateEmployee.goodEmail(emailRepeatField.getText());

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE login SET email='" + emailNewField.getText() +
                    "' WHERE token='" + tokenField.getText() + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

}
