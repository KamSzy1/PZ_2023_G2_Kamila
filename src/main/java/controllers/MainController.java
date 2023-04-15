package controllers;

import com.example.system.Main;
import com.example.system.StageChanger;
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

    public void buttonsHandler(ActionEvent event) throws IOException {
        //Logowanie
        if (event.getSource() == loginButton) {
            login();
        }

        //Przejście do gridu z panelem tokenu
        if (event.getSource() == regChangeButton) {
            backButton.setVisible(true);
            logRegLabel.setText("Rejestracja");
            gridToken.toFront();
        }

        //Sprawdzenie tokenu i przejście dalej - na razie na sztywno
        if (event.getSource() == tokenButton) {
            if(tokenField.getText().equals("1234")){
                gridRegistration.toFront();
            }else{
                wrongTokenLabel.setText("Nieprawidłowy token");
            }

        }

        //
        if (event.getSource() == registrationButton) {

            registration();

        }

        //Powrót z rejestracji do loginu
        if (event.getSource() == backButton) {
            logRegLabel.setText("Logowanie");
            backButton.setVisible(false);
            wrongLogin.setText("");
            gridLogin.toFront();
        }

    }

    //Metoda do logowania
    public void login() throws IOException {

        //To jest po to, aby zmienić scene/wyświetlany panel
        StageChanger stageChanger = new StageChanger();

        //Sprawdzanie pól emaila oraz hasła -> na razie wszystko na sztywno ustawione
        if (emailField.getText().equals("manager") && passwordField.getText().equals("123")) {
            stageChanger.changeScene("/manager.fxml");
            stageChanger.changeSize(1215, 630);
        } else if (emailField.getText().equals("admin") && passwordField.getText().equals("123")) {
            stageChanger.changeScene("/admin.fxml");
            stageChanger.changeSize(1215, 630);
        } else if (emailField.getText().equals("employee") && passwordField.getText().equals("123")) {
            stageChanger.changeScene("/employee.fxml");
            stageChanger.changeSize(1215, 630);
        } else if (emailField.getText().isEmpty() && emailField.getText().isEmpty()) {
            wrongLogin.setText("Uzupełnij wszystkie pola!");
        } else {
            wrongLogin.setText("Zły email bądź hasło");
        }

    }

    //Metoda do rejestrowania użytkowników
    public void registration() throws IOException {

        //To jest po to, aby zmienić scene/wyświetlany panel
        StageChanger stageChanger = new StageChanger();

        //Pobranie wszystkich danych
        String email, password, repeat_password;

        email = emailField.getText();
        password = passwordField.getText();
        repeat_password = repeatPasswordField.getText();

        boolean everythingOk = false;

        //Te metody znajdują się wklasie ValidateData w folderze "other"
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
