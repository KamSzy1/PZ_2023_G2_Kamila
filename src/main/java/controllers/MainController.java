package controllers;

import com.example.system.StageChanger;
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
import other.PasswordHash;
import validate.ValidateEmployee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasa obsługującaq panel główny
 */
public class MainController {

    /**
     * Przycisk powrotu
     */
    @FXML
    private Button backButton;
    /**
     * Przycisk do resetowania hasła
     */
    @FXML
    private Button resetSetNewPasswordButton;
    /**
     * Przycisk do przejścia do panelu resetowania hasła
     */
    @FXML
    private Button resetPasswordButton;
    /**
     * Siatka w panelu logowania
     */
    @FXML
    private GridPane gridLogin;
    /**
     * Siatka w panelu sprawdzania tokenu
     */
    @FXML
    private GridPane gridToken;
    /**
     * Siatka w panelu rejestracji
     */
    @FXML
    private GridPane gridRegistration;
    /**
     * Siatka w panelu resetowania hasła
     */
    @FXML
    private GridPane gridResetPassword;
    /**
     * Pole tekstowe dla e-mail'a w panelu logowania
     */
    @FXML
    private TextField emailField;
    /**
     * Pole tekstowe dla e-mail'a w panelu rejestacji
     */
    @FXML
    private TextField regEmailField;
    /**
     * Pole tekstowe dla hasła w panelu logowania
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Pole tekstowe dla hasła w panelu rejestracji
     */
    @FXML
    private PasswordField regPasswordField;
    /**
     * Pole tekstowe dla powtórzonego hasła w panelu rejestracji
     */
    @FXML
    private PasswordField regRepeatPasswordField;
    /**
     * Pole tekstowe dla tokenu
     */
    @FXML
    private TextField tokenField;
    /**
     * Pole tekstowe dla hasła w panelu resetu hasła
     */
    @FXML
    private TextField resetPasswordField;
    /**
     * Pole tekstowe dla powtórzonego hasła w panelu resetu hasła
     */
    @FXML
    private TextField resetRepeatPasswordField;
    /**
     * Przycisk do logowania
     */
    @FXML
    private Button loginButton;
    /**
     * Przycisk do sprawdzania tokenu
     */
    @FXML
    private Button tokenButton;
    /**
     * Przucisk do rejestracji
     */
    @FXML
    private Button registrationButton;
    /**
     * Przycisk do przejścia do panelu rejestracji
     */
    @FXML
    private Button regChangeButton;
    /**
     * Teskt wyświetlający się w przypadku błędu podczas logowania
     */
    @FXML
    private Label wrongLogin;
    /**
     * Tekst wyświetlający się w przypadku błędy podczas rejestracji
     */
    @FXML
    private Label wrongRegistration;
    /**
     * Tekst wyświetlający się w przypadku błędu podczas resetu hasła
     */
    @FXML
    private Label wrongResetLabel;
    /**
     * Tekst wyświeltający się w przypadku błędu podczas sprawania tokenu
     */
    @FXML
    private Label wrongTokenLabel;
    /**
     * Nagłówek wyświetlający się w panelu
     */
    @FXML
    private Label logRegLabel;
    /**
     * Przywitanie użytkownika w panelu resetu hasła
     */
    @FXML
    private Label resetWelcomeLabel;
    /**
     * Przywitanie użytkownika w panelu rejestracji
     */
    @FXML
    private Label regWelcomeLabel;

    /**
     * Statyczna metoda dla wyboru użytkownika
     */
    private static int USER_CHOICE;
    /**
     * Zmienna z nazwą tokenu
     */
    private String token;
    /**
     * Zmienna do zmiany okienka
     */
    private final StageChanger stageChanger = new StageChanger();
    /**
     * Zmienna z hasłem użytkownika
     */
    private String password;
    /**
     * Zmienna z emailem użytkownika
     */
    private String email;

    /**
     * Metoda, która wykonuje się na samym początku uruchomienia się klasy. Służy do wczytania odpowiednich ustawień w panelu
     */
    @FXML
    public void initialize() {
        gridLogin.toFront();
    }


    /**
     * Metoda do zarządzania wszystkimi przyciskami
     *
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException Wyrzucany wyjątek
     */
    @FXML
    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == loginButton) {
            tryLoginUser();
        } else if (source == regChangeButton) {
            //Przejście do gridu z panelem tokenu
            backButton.setVisible(true);
            logRegLabel.setText("Rejestracja");
            wrongTokenLabel.setText("");
            tokenField.clear();
            gridToken.toFront();
            USER_CHOICE = 1;
        } else if (source == registrationButton) {
            tryRegisterEmployee();
        } else if (source == resetPasswordButton) {
            logRegLabel.setText("Resetowanie hasła");
            wrongTokenLabel.setText("");
            tokenField.clear();
            backButton.setVisible(true);
            gridToken.toFront();
            USER_CHOICE = 2;
        } else if (source == resetSetNewPasswordButton) {
            password = resetPasswordField.getText();
            String repeatPassword = resetRepeatPasswordField.getText();
            changePassword(password, repeatPassword, token);
        } else if (source == tokenButton) {
            //Sprawdzenie tokenu i odesłanie użytkownika w dobre miejsce
            tokenCheck();
        } else if (source == backButton) {
            //Powrót do loginu
            emailField.clear();
            passwordField.clear();
            regEmailField.clear();
            regPasswordField.clear();
            regRepeatPasswordField.clear();

            wrongTokenLabel.setText("");
            logRegLabel.setText("Logowanie");
            backButton.setVisible(false);
            wrongLogin.setText("");
            gridLogin.toFront();
        }
    }

    /**
     * Metoda próbująca zalogowac użytkownika
     */
    private void tryLoginUser() {
        email = emailField.getText();
        password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            wrongLogin.setText("Uzupełnij wszystkie pola!");
        } else {
            login(email, password);
        }
    }


    /**
     * Metoda do logowania użytkownika
     *
     * @param email Email użytkownika
     * @param password Hasło użytkownika
     */
    private void login(String email, String password) {
        try {
            DatabaseConnector.connect();
            String hashedPassword = PasswordHash.hashedPassword(password);
            ResultSet result = QExecutor.executeSelect("SELECT * FROM users " +
                    "INNER JOIN login ON login.user_id = users.id_user " +
                    "WHERE email = '" + email + "' and password = '" + hashedPassword + "'");
            result.next();
            UsersTable.setIdLoginUser(result.getInt("id_user"));
            UsersTable.setLoginName(result.getString("name"));
            UsersTable.setLoginSurname(result.getString("surname"));
            UsersTable.setGroupNumber(result.getInt("groups"));
            if (result.getInt("position_id") == 1) {
                stageChanger.changeScene("/admin.fxml");
                stageChanger.changeSize(1215, 630);
            } else if (result.getInt("position_id") == 2) {
                stageChanger.changeScene("/manager.fxml");
                stageChanger.changeSize(1215, 630);
            } else {
                stageChanger.changeScene("/employee.fxml");
                stageChanger.changeSize(1215, 630);
            }

        } catch (SQLException | IOException e) {
            wrongLogin.setText("Zły email bądź hasło");
            e.printStackTrace();
        }
    }

    /**
     * Sprawdzenie tokenu
     */
    private void tokenCheck() {
        try {
            token = tokenField.getText();
            if (token.isEmpty()) {
                wrongTokenLabel.setText("Pusty token!");
            } else {
                getUserFromToken(token);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            wrongTokenLabel.setText("Błędny token");
        }
    }

    /**
     * Metoda do pozyskania informacji o użytkowniku na podstawie tokenu
     *
     * @param token Token użytkownika
     * @throws SQLException Wyrzucany wyjątek
     */
    private void getUserFromToken(String token) throws SQLException {
        ResultSet result = QExecutor.executeSelect("SELECT u.name, u.surname, u.token, l.email FROM login AS l " +
                "INNER JOIN users AS u ON u.id_user = l.user_id " +
                "WHERE token = '" + token + "'");
        result.next();

        UsersTable user = new UsersTable();
        user.setName(result.getString("name"));
        user.setSurname(result.getString("surname"));

        if (USER_CHOICE == 1) {
            if (result.getString("email") == null) {
                regWelcomeLabel.setText("Witaj " + user.getName() + " " + user.getSurname() + "! W poniższe pola wpisz dane, którymi będziesz logował się do systemu");
                gridRegistration.toFront();
            } else {
                wrongTokenLabel.setText("Taki użytkownik już istnieje!");
            }
        } else if (USER_CHOICE == 2) {
            resetWelcomeLabel.setText("Witaj " + user.getName() + " " + user.getSurname() + "! Wpisz swoje nowe hasło");
            gridResetPassword.toFront();
        }
    }

    /**
     * Metoda do próby rejestracji użytkownika
     */
    private void tryRegisterEmployee() {
        // Rejestracja użytkownika
        email = regEmailField.getText();
        password = regPasswordField.getText();
        String repeat_password = regRepeatPasswordField.getText();

        try {
            ValidateEmployee.goodEmail(email);
            ValidateEmployee.samePassword(password, repeat_password);

            registration(email, password, token);
            stageChanger.changeScene("/main.fxml");
        } catch (Exception e) {
            wrongRegistration.setText(e.getMessage());
        }
    }

    /**
     * Rejestracja użytkownika
     *
     * @param mail Email użytkownika
     * @param password Hasło użytkownika
     * @param token Token użytkownika
     * @throws SQLException
     */
    private void registration(String mail, String password, String token) throws SQLException {
        String hashedPassword = PasswordHash.hashedPassword(password);

        DatabaseConnector.connect();
        QExecutor.executeQuery("UPDATE login " +
                "INNER JOIN user ON users.id_user = login.user_id" +
                "SET email = " + mail + ", password = " + hashedPassword + " " +
                "WHERE users.token = '" + token + "'");

    }

    /**
     * Metoda do zmiany hasła
     *
     * @param password Hasło
     * @param repeatPassword Powtórzone hasło
     * @param token Token
     */
    private void changePassword(String password, String repeatPassword, String token) {
        try {

            ValidateEmployee.samePassword(password, repeatPassword);
            String hashedPassword = PasswordHash.hashedPassword(password);
            DatabaseConnector.connect();
            QExecutor.executeQuery("UPDATE login " +
                    "INNER JOIN users ON users.id_user = login.user_id " +
                    "SET password = '" + hashedPassword + "' " +
                    "WHERE users.token = '" + token + "'");
        } catch
        (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            wrongResetLabel.setText(e.getMessage());
        }

    }

}
