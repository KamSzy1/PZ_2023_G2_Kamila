package controllers_pop_settings;

import database.DatabaseConnector;
import database.QExecutor;
import database_classes.UsersTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import other.ButtonManager;
import other.PasswordHash;
import validate.ValidateEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasa do zarządzania panelem edytowania hasła
 */
public class EditPasswordInSettingsController {

    /**
     * Przycisk wyłączenia okna
     */
    @FXML
    private Button cancel2Button;
    /**
     * Przycisk wyłączenia okna
     */
    @FXML
    private Button cancelButton;
    /**
     * Przycisk sprawdzania tokenu
     */
    @FXML
    private Button continueButton;
    /**
     * Siatka dla zmiany hasła
     */
    @FXML
    private GridPane passwordGrid;
    /**
     * Pole tekstowe do wpisania nowego hasła
     */
    @FXML
    private PasswordField passwordNewField;
    /**
     * Pole tekstowe do wisania powtórzonego, nowego hasła
     */
    @FXML
    private PasswordField passwordRepeatField;
    /**
     * Przycisk do akceptacji zmiany hasła
     */
    @FXML
    private Button saveButton;
    /**
     * Pole tekstowe dla tokenu
     */
    @FXML
    private TextField tokenField;
    /**
     * Siatka dla sprawdzania tokenu
     */
    @FXML
    private GridPane tokenGrid;
    /**
     * Tekst, który się wyświetli w przypadku błędu
     */
    @FXML
    private Label wrongLabel;
    /**
     * Tekst, który wyświetli się w przupadku błędnego tokenu
     */
    @FXML
    private Label wrongTokenLabel;

    /**
     * @param token Zmienna
     */
    private String token;
    /**
     * Zarządzanie przyciskami
     */
    private ButtonManager buttonManager = new ButtonManager();

    /**
     * Metoda, która wykonuje się na samym początku uruchomienia się klasy. Służy do wczytania odpowiednich ustawień w panelu
     */
    @FXML
    public void initialize() {
        tokenGrid.toFront();
    }

    /**
     * Metoda do zarządzania wszystkimi przyciskami
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException Wyrzucany wyjątek
     */
    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == continueButton) {
            //Sprawdzenie tokenu
            checkIfTokenIsEmpty();
        } else if (source == saveButton) {
            resetPassword();
        } else if (source == cancel2Button) {
            buttonManager.closeWindow(cancelButton);
        } else if (source == cancelButton) {
            buttonManager.closeWindow(cancelButton);
        }
    }

    /**
     * Sprawdzenie pola z tokenem
     */
    private void checkIfTokenIsEmpty() {
        try {
            token = tokenField.getText();
            DatabaseConnector.connect();
            ResultSet result = QExecutor.executeSelect("SELECT * FROM login " +
                    "INNER JOIN users on users.id_user = login.user_id " +
                    "WHERE users.token ='" + token + "' " +
                    "AND users.id_user = " + UsersTable.getIdLoginUser());
            result.next();

            if (result.getString("password").isEmpty()) {
                wrongTokenLabel.setText("Błędny token");
            } else {
                passwordGrid.toFront();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            wrongTokenLabel.setText("Błędny token");
        }
    }

    /**
     * Resetowanie hasła
     */
    private void resetPassword() {
        try {
            String newPassword = passwordNewField.getText();
            String repeatNewPassword = passwordRepeatField.getText();

            ValidateEmployee.samePassword(newPassword, repeatNewPassword);
            String hashedPassword = PasswordHash.hashedPassword(newPassword);

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE login " +
                    "INNER JOIN users ON users.id_user = login.user_id " +
                    "SET password = '" + hashedPassword + "' " +
                    "WHERE users.token = '" + token + "' " +
                    "AND users.id_user = " + UsersTable.getIdLoginUser());
            buttonManager.closeWindow(saveButton);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }
}
