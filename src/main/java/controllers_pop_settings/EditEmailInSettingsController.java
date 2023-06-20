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
import other.ButtonManager;
import validate.ValidateEmployee;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasa do zarządzania panelem edytowania e-mail'a
 */
public class EditEmailInSettingsController {

    /**
     * Przycisk wyłączenia okna
     */
    @FXML
    private Button cancelButton;
    /**
     * Przycisk wyłączenia okna
     */
    @FXML
    private Button cancel2Button;
    /**
     * Przycisk sprawdzania tokenu
     */
    @FXML
    private Button continueButton;
    /**
     * Przycisk do akceptacji zmiany e-mail'a
     */
    @FXML
    private Button saveButton;
    /**
     * Siatka dla zmiany e-mail
     */
    @FXML
    private GridPane emailGrid;
    /**
     * Siatka dla sprawdzania tokenu
     */
    @FXML
    private GridPane tokenGrid;
    /**
     * Aktualny e-mail
     */
    @FXML
    private TextField emailActualField;
    /**
     * Pole tekstowe do wpisania nowego e-mail'a
     */
    @FXML
    private TextField emailNewField;
    /**
     * Pole tekstowe do wisania powtórzonego, nowego e-mail'a
     */
    @FXML
    private TextField emailRepeatField;
    /**
     * Pole tekstowe dla tokenu
     */
    @FXML
    private TextField tokenField;
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
     */
    public void buttonsHandler(ActionEvent event) {
        Object source = event.getSource();

        if (source == continueButton) {
            checkIfTokenIsEmpty();
        } else if (source == saveButton) {
            changeEmail();
        } else if (source == cancelButton) {
            buttonManager.closeWindow(cancelButton);
        } else if (source == cancel2Button) {
            buttonManager.closeWindow(cancel2Button);
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
}
