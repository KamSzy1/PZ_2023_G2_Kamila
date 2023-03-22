package controllers;

import com.example.system.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationControler {

    //Wpisane przykładowo, bo tak
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
    TextField mailField;

    @FXML
    TextField passwordField;

    @FXML
    TextField repeatPasswordField;

    //Przycisk powrotu
    public void goBack(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/mainPage.fxml");
    }

    //Rejestracja użytkownika
    public void registrationUser(ActionEvent event) throws IOException {
        //Tutaj trzeba dodać kod dodawania nowego użytkownika do bazy danych

    }
}
