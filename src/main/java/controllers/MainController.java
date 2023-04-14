package controllers;

import com.example.system.Main;
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
    private GridPane gridRegistration;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registrationButton;
    @FXML
    private Button regChangeButton;
    @FXML
    private Label wrongLogin;
    @FXML
    private Label logRegLabel;

    public void buttonsHandler(ActionEvent event) throws IOException{
        //Logowanie
        if(event.getSource() == loginButton){
            login();
        }

        //Przejście do gridu z panelem rejestracji
        if(event.getSource() == regChangeButton){
            wrongLogin.setText("");
            logRegLabel.setText("Rejestracja");
            gridRegistration.toFront();
        }

        //Rejestracja użytkownika
        if(event.getSource() == registrationButton){
            registration();
        }

        //Powrót z rejestracji do loginu
        if(event.getSource() == backButton){
            logRegLabel.setText("Logowanie");
            gridLogin.toFront();
        }

    }

    //Metoda do logowania
    public void login() throws IOException {

        //To jest po to, aby zmienić scene/wyświetlany panel
        Main main = new Main();

        //Sprawdzanie pól emaila oraz hasła -> na razie wszystko na sztywno ustawione
        if (emailField.getText().equals("manager") && passwordField.getText().equals("123")) {
            main.changeScene("/manager.fxml");
        } else if (emailField.getText().equals("admin") && passwordField.getText().equals("123")) {
            main.changeScene("/admin.fxml");
        } else if (emailField.getText().equals("employee") && passwordField.getText().equals("123")) {
            main.changeScene("/employee.fxml");
        } else if (emailField.getText().isEmpty() && emailField.getText().isEmpty()) {
            wrongLogin.setText("Uzupełnij wszystkie pola!");
        } else {
            wrongLogin.setText("Zły email bądź hasło");
        }

    }

    //Metoda do rejestrowania użytkowników
    public void registration() throws IOException {

//        //To jest po to, aby zmienić scene/wyświetlany panel
//        Main main = new Main();
//
//        //Pobranie wszystkich danych
//        String name, surname, address, zipCode, phoneNumber, place, email, password, repeat_password;
//
//        name = nameField.getText();
//        surname = surnameField.getText();
//        address = addressField.getText();
//        zipCode = zipCodeField.getText();
//        place = placeField.getText();
//        phoneNumber = phoneNumberField.getText();
//        email = emailField.getText();
//        password = passwordField.getText();
//        repeat_password = repeatPasswordField.getText();
//
//        boolean everythingOk = false;
//        //Te metody znajdują się wklasie ValidateData w folderze "other"
//        try {
//            ValidateData.goodName(name);
//            ValidateData.goodSurname(surname);
//            ValidateData.goodAddress(address);
//            ValidateData.goodEmail(email);
//            ValidateData.samePassword(password, repeat_password);
//            ValidateData.goodZipCode(zipCode);
//            ValidateData.goodPlace(place);
//            ValidateData.goodPhoneNumber(phoneNumber);
//            everythingOk = true;
//
//            wrongRegistration.setText("Zarejestrowano pomyślnie");
//        } catch (Exception e) {
//            wrongRegistration.setText(e.getMessage());
//        }
//
//        //Jeśli wszyskie dane są poprawne, to doda się do bazy danych wszystko
//        if (everythingOk) {
//
//            password = PasswordHash.hashedPassword(password);
//            System.out.println(password);
//
//            //Tutaj powinien pojawić się kod dodawnia do bazy danych
//
//
//            //Po wykonaniu się wszystkiego zmieni się panel [na portzeby testów na razie kod jest w komentarzu]
//            //main.changeScene("/main.fxml");
//
//        }

    }
}
