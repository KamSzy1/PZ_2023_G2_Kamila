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
import other.ValidateData;

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

    @FXML
    public void initialize() {
        gridLogin.toFront();
    }

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();
        String password;
        String email;

        if (source == loginButton) {
            //Logowanie
            email = emailField.getText();
            password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                wrongLogin.setText("Uzupełnij wszystkie pola!");
            } else {
                login(email, password);
            }

        } else if (source == regChangeButton) {
            //Przejście do gridu z panelem tokenu
            backButton.setVisible(true);
            logRegLabel.setText("Rejestracja");
            tokenField.clear();
            gridToken.toFront();
            USER_CHOICE = 1;
        } else if (source == registrationButton) {
            // Rejestracja użytkownika
            email = regEmailField.getText();
            password = regPasswordField.getText();
            String repeat_password = regRepeatPasswordField.getText();

            try {
                ValidateData.goodEmail(email);
                ValidateData.samePassword(password, repeat_password);

                registration(email, password, token);
                stageChanger.changeScene("/main.fxml");
            } catch (Exception e) {
                wrongRegistration.setText(e.getMessage());
            }
        } else if (source == resetPasswordButton) {
            backButton.setVisible(true);
            gridToken.toFront();
            USER_CHOICE = 2;
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

    //Metoda do logowania
    private void login(String email, String password) {
        try {
            DatabaseConnector.connect();
            String hashedPassword = PasswordHash.hashedPassword(password);
            ResultSet result = QExecutor.executeSelect("SELECT * FROM users inner join login ON login.token = users.token " +
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
        }
    }

    private void getUserFromToken(String token) throws SQLException {
        ResultSet result = QExecutor.executeSelect("SELECT * FROM login WHERE token ='" + token + "'");
        result.next();

        ResultSet person = QExecutor.executeSelect("SELECT name, surname FROM users WHERE token ='" + token + "'");
        person.next();

        UsersTable user = new UsersTable();
        user.setName(person.getString("name"));
        user.setSurname(person.getString("surname"));
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


    //Metoda do rejestrowania użytkowników
    private void registration(String mail, String password, String token) throws SQLException {
        String hashedPassword = PasswordHash.hashedPassword(password);

        DatabaseConnector.connect();
        QExecutor.executeQuery("UPDATE login set email = '" + mail + "', password = '" + hashedPassword + "' where token= '" + token + "'");
    }
}
