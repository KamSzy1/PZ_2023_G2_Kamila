package controllers;

import com.example.system.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import other.PasswordHash;
import other.ValidateData;
import java.io.IOException;
public class RegistrationControler {

    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField addressField;
    @FXML
    TextField zipCodeField;
    @FXML
    TextField placeField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    TextField repeatPasswordField;
    @FXML
    private Label wrongRegistration;

    //Przycisk powrotu
    public void goBack(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/mainPage.fxml");
    }

    //Rejestracja użytkownika
    public void registrationUser(ActionEvent event) throws IOException {
        registration();
    }

    //Metoda do rejestrowania użytkowników
    public void registration() throws IOException {

        //To jest po to, aby zmienić scene/wyświetlany panel
        Main main = new Main();

        //Pobranie wszystkich danych
        String name, surname, address, zipCode, phoneNumber, place, email, password, repeat_password;

        name = nameField.getText();
        surname = surnameField.getText();
        address = addressField.getText();
        zipCode = zipCodeField.getText();
        place = placeField.getText();
        phoneNumber = phoneNumberField.getText();
        email = emailField.getText();
        password = passwordField.getText();
        repeat_password = repeatPasswordField.getText();

        boolean everythingOk = false;
        //Te metody znajdują się wklasie ValidateData w folderze "other"
        try{
            ValidateData.goodName(name);
            ValidateData.goodSurname(surname);
            ValidateData.goodAddress(address);
            ValidateData.goodEmail(email);
            ValidateData.samePassword(password, repeat_password);
            ValidateData.goodZipCode(zipCode);
            ValidateData.goodPlace(place);
            ValidateData.goodPhoneNumber(phoneNumber);
            everythingOk = true;

            wrongRegistration.setText("Zarejestrowano pomyślnie");
        }
        catch (Exception e){
            wrongRegistration.setText(e.getMessage());
        }

        //Jeśli wszyskie dane są poprawne, to doda się do bazy danych wszystko
        if(everythingOk){

            //Hashowanie hasła -> sprawdzić czy przez static w Password Hash się nic nie wywali
            //PasswordHash passwordhash = new PasswordHash();

            password = PasswordHash.hashedPassword(password);
            System.out.println(password);

            //Tutaj powinien pojawić się kod dodawnia do bazy danych



            //Po wykonaniu się wszystkiego zmieni się panel [na portzeby testów na razie kod jest w komentarzu]
            //main.changeScene("/mainPage.fxml");

        }

    }
}
