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
    @FXML
    private Label wrongLabel;
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
        String token = tokenField.getText();

        if (token.isEmpty()) {
            wrongTokenLabel.setText("Pusty token!");
        } else {
            checkToken(token);
        }
    }

    //Sprawdzenie tokenu
    private void checkToken(String token) {
        try {
            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT login.email FROM login " +
                    "INNER JOIN users on users.id_user = login.user_id " +
                    "WHERE users.token ='" + token + "'");
            result.next();

            if (result.getString("email").isEmpty()) {
                wrongTokenLabel.setText("Błędny token");
            } else {
                emailGrid.toFront();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            wrongTokenLabel.setText("Błędny token");
        }
    }

    //Zmiana adresu Email
    private void changeEmail() {
        try {
            String oldEmail = emailActualField.getText();
            String newEmail = emailNewField.getText();
            String newRepeatEmail = emailRepeatField.getText();

            ValidateEmployee.goodEmail(newEmail);
            ValidateEmployee.sameEmail(newEmail, newRepeatEmail);

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE login SET email='" + newEmail +
                    "' WHERE email = '" + oldEmail + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }

    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

}
