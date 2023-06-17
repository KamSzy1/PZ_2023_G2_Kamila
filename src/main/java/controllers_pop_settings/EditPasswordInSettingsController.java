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
import other.PasswordHash;
import validate.ValidateEmployee;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Klasa do zarządzania panelem edytowania hasła
 */
public class EditPasswordInSettingsController {

    /**
     * @param cancel2Button Przycisk wyłączenia okna
     * @param cancelButton Przycisk wyłączenia okna
     * @param continueButton Przycisk sprawdzania tokenu
     * @param passwordGrid Siatka dla zmiany hasła
     * @param passwordNewField Pole tekstowe do wpisania nowego hasła
     * @param passwordRepeatField Pole tekstowe do wisania powtórzonego, nowego hasła
     * @param saveButton Przycisk do akceptacji zmiany hasła
     * @param tokenField Pole tekstowe dla tokenu
     * @param tokenGrid Siatka dla sprawdzania tokenu
     * @param wrongLabel Tekst, który się wyświetli w przypadku błędu
     * @param wrongTokenLabel Tekst, który wyświetli się w przupadku błędnego tokenu
     */
    @FXML
    private Button cancel2Button;
    @FXML
    private Button cancelButton;
    @FXML
    private Button continueButton;
    @FXML
    private GridPane passwordGrid;
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
    private Label wrongTokenLabel;

    /**
     * @param token Zmienna
     */
    private String token;

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
     * @throws IOException
     */
    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == continueButton) {
            //Sprawdzenie tokenu
            checkIfTokenIsEmpty();
        } else if (source == saveButton) {
            resetPassword();
        } else if (source == cancel2Button) {
            //Zamykanie okienka
            Stage stage = (Stage) cancel2Button.getScene().getWindow();
            stage.close();
        } else if (source == cancelButton) {
            //Zamykanie okienka
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Sprawdzenie pola z tokenem
     */
    private void checkIfTokenIsEmpty() {
        token = tokenField.getText();

        try {
            ValidateEmployee.goodToken(token);
        } catch (Exception e) {
            wrongTokenLabel.setText(e.getMessage());
        }
        passwordGrid.toFront();
    }

    /**
     * Resetowanie hasła
     */
    private void resetPassword() {
        //Zmiana hasła
        String newPassword = passwordNewField.getText();
        String repeatNewPassword = passwordRepeatField.getText();

        try {
            ValidateEmployee.samePassword(newPassword, repeatNewPassword);
            String hashedPassword = PasswordHash.hashedPassword(newPassword);

            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE login " +
                    "INNER JOIN users ON users.id_user = login.user_id " +
                    "SET password = '" + hashedPassword + "' " +
                    "WHERE users.token = '" + token +"' " +
                    "AND users.id_user = " + UsersTable.getIdLoginUser());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            wrongLabel.setText(e.getMessage());
        }
    }
}
