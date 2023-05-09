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
    private GridPane gridLogin;
    @FXML
    private GridPane gridToken;
    @FXML
    private GridPane gridRegistration;
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

    public static int id_user;
    String email;
    String password;
    String repeat_password;
    String token;
    StageChanger stageChanger = new StageChanger();
    UsersTable usersTable = new UsersTable();

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();

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
        } else if (source == tokenButton) {
            //Sprawdzenie tokenu i przejście dalej

            try {
                if (tokenField.getText().isEmpty()) {
                    wrongTokenLabel.setText("Pusty token!");
                } else {
                    ResultSet result = QExecutor.executeSelect("SELECT * FROM login WHERE token ='" + tokenField.getText() + "'");
                    result.next();
                    if (result.getString("email") == null) {
                        token = tokenField.getText();
                        gridRegistration.toFront();
                    } else {
                        wrongTokenLabel.setText("Taki użytkownik już istnieje!");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (source == registrationButton) {
            // Rejestracja użytkownika

            boolean isEverythingOk = false;

            //Pobranie wszystkich danych
            email = regEmailField.getText();
            password = regPasswordField.getText();
            repeat_password = regRepeatPasswordField.getText();

            //Te metody znajdują się w klasie ValidateData w package "other"
            try {
                ValidateData.goodEmail(email);
                ValidateData.samePassword(password, repeat_password);
                isEverythingOk = true;
            } catch (Exception e) {
                wrongRegistration.setText(e.getMessage());
            }

            //Jeśli wszyskie dane są poprawne, to doda się do bazy danych wszystko
            if (isEverythingOk) {
                registration(email, password, token);
                stageChanger.changeScene("/main.fxml");
            } else {
                wrongRegistration.setText("Coś poszło nie tak. Spróbuj później");
            }
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
    public void login(String email, String password) throws IOException {
        DatabaseConnector.connect();

        try {
            String hashedPassword = PasswordHash.hashedPassword(password);
            ResultSet result = QExecutor.executeSelect("SELECT * FROM users inner join login ON login.token = users.token WHERE email = '" + email + "' and password = '" + hashedPassword + "'");
            result.next();
            usersTable.setLoginName(result.getString("name"));
            usersTable.setLoginSurname(result.getString("surname"));
            usersTable.setLoginIdUser(result.getInt("id_user"));
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

        } catch (SQLException throwables) {
            wrongLogin.setText("Zły email bądź hasło");
            throwables.printStackTrace();
        }

    }

    //Metoda do rejestrowania użytkowników
    public void registration(String mail, String password, String token) {

        String hashedPassword = PasswordHash.hashedPassword(password);

        DatabaseConnector.connect();
        QExecutor.executeQuery("UPDATE login set email = '" + mail + "', password = '" + hashedPassword + "' where token= '" + token + "'");

    }
}
