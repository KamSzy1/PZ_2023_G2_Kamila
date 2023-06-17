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
     * @param backButton Przycisk powrotu
     * @param resetSetNewPasswordButton Przycisk do resetowania hasła
     * @param resetPasswordButton Przycisk do przejścia do panelu resetowania hasła
     * @param gridLogin Siatka w panelu logowania
     * @param gridToken Siatka w panelu sprawdzania tokenu
     * @param gridRegistration Siatka w panelu rejestracji
     * @param gridResetPassword Siatka w panelu resetowania hasła
     * @param emailField Pole tekstowe dla e-mail'a w panelu logowania
     * @param regEmailField Pole tekstowe dla e-mail'a w panelu rejestacji
     * @param passwordField Pole tekstowe dla hasła w panelu logowania
     * @param regPasswordField Pole tekstowe dla hasła w panelu rejestracji
     * @param regRepeatPasswordField Pole tekstowe dla powtórzonego hasła w panelu rejestracji
     * @param tokenField Pole tekstowe dla tokenu
     * @param resetPasswordField Pole tekstowe dla hasła w panelu resetu hasła
     * @param resetRepeatPasswordField Pole tekstowe dla powtórzonego hasła w panelu resetu hasła
     * @param loginButton Przycisk do logowania
     * @param tokenButton Przycisk do sprawdzania tokenu
     * @param registrationButton Przucisk do rejestracji
     * @param regChangeButton Przycisk do przejścia do panelu rejestracji
     * @param wrongLogin Teskt wyświetlający się w przypadku błędu podczas logowania
     * @param wrongRegistration Tekst wyświetlający się w przypadku błędy podczas rejestracji
     * @param wrongResetLabel Tekst wyświetlający się w przypadku błędu podczas resetu hasła
     * @param wrongTokenLabel Tekst wyświeltający się w przypadku błędu podczas sprawania tokenu
     * @param logRegLabel Nagłówek wyświetlający się w panelu
     * @param resetWelcomeLabel Przywitanie użytkownika w panelu resetu hasła
     * @param regWelcomeLabel Przywitanie użytkownika w panelu rejestracji
     */
    @FXML
    private Button backButton;
    @FXML
    private Button resetSetNewPasswordButton;
    @FXML
    private Button resetPasswordButton;
    @FXML
    private GridPane gridLogin;
    @FXML
    private GridPane gridToken;
    @FXML
    private GridPane gridRegistration;
    @FXML
    private GridPane gridResetPassword;
    @FXML
    private TextField emailField;
    @FXML
    private TextField regEmailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField regPasswordField;
    @FXML
    private PasswordField regRepeatPasswordField;
    @FXML
    private TextField tokenField;
    @FXML
    private TextField resetPasswordField;
    @FXML
    private TextField resetRepeatPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button tokenButton;
    @FXML
    private Button registrationButton;
    @FXML
    private Button regChangeButton;
    @FXML
    private Label wrongLogin;
    @FXML
    private Label wrongRegistration;
    @FXML
    private Label wrongResetLabel;
    @FXML
    private Label wrongTokenLabel;
    @FXML
    private Label logRegLabel;
    @FXML
    private Label resetWelcomeLabel;
    @FXML
    private Label regWelcomeLabel;

    /**
     * @param USER_CHOICE Statyczna metoda dla wyboru użytkownika
     * @param token Zmienna z nazwą tokenu
     * @param stageChanger Zmienna do zmiany okienka
     * @param password Zmienna z hasłem użytkownika
     * @param email Zmienna z emailem użytkownika
     */
    private static int USER_CHOICE;
    private String token;
    private final StageChanger stageChanger = new StageChanger();
    private String password;
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
     * @param event Służy do prawidłowego zarządzania okienkami
     * @throws IOException
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
     * @param token Token użytkownika
     * @throws SQLException
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
