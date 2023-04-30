package controllers;

import com.example.system.Main;
import com.example.system.StageChanger;
import database.DatabaseConnector;
import database.QExecutor;
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
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
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

    public void buttonsHandler(ActionEvent event) throws IOException {
        Object source = event.getSource();
        //Logowanie
        if (source == loginButton) {
            email = emailField.getText();
            password = passwordField.getText();

            login(email, password);
        }

        //Przejście do gridu z panelem tokenu
        if (source == regChangeButton) {
            backButton.setVisible(true);
            logRegLabel.setText("Rejestracja");
            gridToken.toFront();
        }

        //Sprawdzenie tokenu i przejście dalej - na razie na sztywno
        if (source == tokenButton) {
            if (tokenField.getText().equals("1234")) {
                gridRegistration.toFront();
            } else {
                wrongTokenLabel.setText("Nieprawidłowy token");
            }
        }

        //
        if (source == registrationButton) {
            registration();
        }

        //Powrót z rejestracji do loginu
        if (source == backButton) {
            logRegLabel.setText("Logowanie");
            backButton.setVisible(false);
            wrongLogin.setText("");
            gridLogin.toFront();
        }

    }

    //Metoda do logowania
    public void login(String email, String password) throws IOException {

        //To jest po to, aby zmienić scene/wyświetlany panel
        StageChanger stageChanger = new StageChanger();
        DatabaseConnector.connect();

        try {
            if (email.isEmpty() && password.isEmpty()) {
                wrongLogin.setText("Uzupełnij wszystkie pola!");
            } else {
                ResultSet result = QExecutor.executeSelect("SELECT * FROM login inner join users ON login.user_id = users.id_user WHERE email = '" + emailField.getText() + "' and password = '" + passwordField.getText() + "'");
                result.next();
                //this.id_user = result.getInt("id_user");
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
            }

        } catch (SQLException throwables) {
            wrongLogin.setText("Zły email bądź hasło");
            throwables.printStackTrace();
        }


        //Sprawdzanie pól emaila oraz hasła -> na razie wszystko na sztywno ustawione
        /*if (email.equals("manager") && password.equals("123")) {
            stageChanger.changeScene("/manager.fxml");
            stageChanger.changeSize(1215, 630);
        } else if (email.equals("admin") && password.equals("123")) {
            stageChanger.changeScene("/admin.fxml");
            stageChanger.changeSize(1215, 630);
        } else if (email.equals("employee") && password.equals("123")) {
            stageChanger.changeScene("/employee.fxml");
            stageChanger.changeSize(1215, 630);
        } else if (email.isEmpty() && password.isEmpty()) {
            wrongLogin.setText("Uzupełnij wszystkie pola!");
        } else {
            wrongLogin.setText("Zły email bądź hasło");
        }*/

    }

    //Metoda do rejestrowania użytkowników
    public void registration() throws IOException {

        //To jest po to, aby zmienić scene/wyświetlany panel
        StageChanger stageChanger = new StageChanger();
        boolean everythingOk = false;

        //Pobranie wszystkich danych
        String email;
        String password;
        String repeat_password;

        email = emailField.getText();
        password = passwordField.getText();
        repeat_password = repeatPasswordField.getText();

        //Te metody znajdują się w klasie ValidateData w folderze "other"
        try {
            ValidateData.goodEmail(email);
            ValidateData.samePassword(password, repeat_password);
            everythingOk = true;

            //Linijkę poniżej można usunąć, jak się zrobi rejesrtację do końca
            wrongRegistration.setText("Zarejestrowano pomyślnie");
        } catch (Exception e) {
            wrongRegistration.setText(e.getMessage());
        }

        //Jeśli wszyskie dane są poprawne, to doda się do bazy danych wszystko
        if (everythingOk) {

            password = PasswordHash.hashedPassword(password);
            System.out.println(password);

            //Tutaj powinien pojawić się kod dodawnia do bazy danych


            //Po wykonaniu się wszystkiego zmieni się panel [na portzeby testów na razie kod jest w komentarzu]
            //stageChanger.changeScene("/main.fxml");

        }

    }
}
