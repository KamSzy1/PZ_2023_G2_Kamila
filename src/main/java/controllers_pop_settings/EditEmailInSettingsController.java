package controllers_pop_settings;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.UsersTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import validate.ValidateEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasa do zarządzania panelem edytowania e-mail'a
 */
public class EditEmailInSettingsController {

    /**
     * @param cancel2Button Przycisk wyłączenia okna
     * @param cancelButton Przycisk wyłączenia okna
     * @param continueButton Przycisk sprawdzania tokenu
     * @param emailGrid Siatka dla zmiany e-mail
     * @param emailActualField Aktualny e-mail
     * @param emailNewField Pole tekstowe do wpisania nowego e-mail'a
     * @param emailRepeatField Pole tekstowe do wisania powtórzonego, nowego e-mail'a
     * @param saveButton Przycisk do akceptacji zmiany e-mail'a
     * @param tokenField Pole tekstowe dla tokenu
     * @param tokenGrid Siatka dla sprawdzania tokenu
     * @param wrongLabel Tekst, który się wyświetli w przypadku błędu
     * @param wrongTokenLabel Tekst, który wyświetli się w przupadku błędnego tokenu
     */
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

    /**
     * Metoda, która wykonuje się na samym początku uruchomienia się klasy. Służy do wczytania odpowiednich ustawień w panelu
     */
    @FXML
    public void initialize() {
        tokenGrid.toFront();
    }

    /**
     * Metoda do zarządzania wszystkimi przyciskami
     * @param event Służy do prawidłowego zarządzania okienkami
     */
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

    /**
     * Sprawdzenie pola z tokenem
     */
    private void checkIfTokenIsEmpty() {
        String token = tokenField.getText();

        if (token.isEmpty()) {
            wrongTokenLabel.setText("Pusty token!");
        } else {
            checkToken(token);
        }
    }

    /**
     * Sprawdzenie tokenu
     *
     * @param token Token użytkownika
     */
    private void checkToken(String token) {
        try {
            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT login.email FROM login " +
                    "INNER JOIN users on users.id_user = login.user_id " +
                    "WHERE users.token ='" + token + "' " +
                    "AND users.id_user = " + UsersTable.getIdLoginUser());
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

    /**
     * Zmiana adresu E-mail
     */
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

    /**
     * Zamykanie okienka
     * @param button Informacja o tym, który przycisk został kliknięty
     */
    private void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

}
