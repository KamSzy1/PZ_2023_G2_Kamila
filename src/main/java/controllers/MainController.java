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

public class MainController {

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

    private static int USER_CHOICE;
    private String token;
    private final StageChanger stageChanger = new StageChanger();
    private String password;
    private String email;

    @FXML
    public void initialize() {
        gridLogin.toFront();
    }

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

        if (source == loginButton) {
            tryLoginUser();
        } else if (source == regChangeButton) {
            //Przejście do gridu z panelem tokenu
            backButton.setVisible(true);
            logRegLabel.setText("Rejestracja");
            tokenField.clear();
            gridToken.toFront();
            USER_CHOICE = 1;
        } else if (source == registrationButton) {
            tryRegisterEmployee();
        } else if (source == resetPasswordButton) {
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
            //Powrót z rejestracji do loginu
            emailField.clear();
            passwordField.clear();
            regEmailField.clear();
            regPasswordField.clear();
            regRepeatPasswordField.clear();

            logRegLabel.setText("Logowanie");
            backButton.setVisible(false);
            wrongLogin.setText("");
            gridLogin.toFront();
        }
    }

    private void tryLoginUser() {
        //Logowanie
        email = emailField.getText();
        password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            wrongLogin.setText("Uzupełnij wszystkie pola!");
        } else {
            login(email, password);
        }
    }

    //Metoda do logowania
    private void login(String email, String password) {
        try {
            DatabaseConnector.connect();
            String hashedPassword = PasswordHash.hashedPassword(password);
            ResultSet result = QExecutor.executeSelect("SELECT * FROM users " +
                    "INNER JOIN login ON login.user_id = users.id_user " +
                    "WHERE email = '" + email + "' and password = '" + hashedPassword + "'");
            result.next();
            UsersTable.setLoginName(result.getString("name"));
            UsersTable.setLoginSurname(result.getString("surname"));
            UsersTable.setIdLoginUser(result.getInt("id_user"));
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

    private void getUserFromToken(String token) throws SQLException {
        ResultSet result = QExecutor.executeSelect("SELECT u.name, u.surname, u.token, l.email FROM login AS l " +
                "INNER JOIN users AS u ON u.id_user = l.user_id " +
                "WHERE token = '" + token + "'");
        result.next();
        System.out.println(token);

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

    //Metoda do rejestrowania użytkowników
    private void registration(String mail, String password, String token) throws SQLException {
        String hashedPassword = PasswordHash.hashedPassword(password);

        DatabaseConnector.connect();
        QExecutor.executeQuery("UPDATE login " +
                "INNER JOIN user ON users.id_user = login.user_id" +
                "SET email = " + mail + ", password = " + hashedPassword + " " +
                "WHERE users.token = '" + token + "'");

    }

    //Zmiana hasła (Zapomniałem hasło)
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
