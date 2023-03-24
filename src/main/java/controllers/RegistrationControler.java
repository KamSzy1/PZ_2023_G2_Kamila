package controllers;

import com.example.system.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    public void registration() throws IOException{

        //To jest po to, aby zmienić scene/wyświetlany panel
        Main main = new Main();

        //Sprawdzanie pól emaila oraz hasła -> na razie wszystko na sztywno ustawione
        if (!nameField.getText().isEmpty()
                && !surnameField.getText().isEmpty()
                && !addressField.getText().isEmpty()
                && !zipCodeField.getText().isEmpty()
                && !phoneNumberField.getText().isEmpty()
                && !emailField.getText().isEmpty()
                && !passwordField.getText().isEmpty()
                && !repeatPasswordField.getText().isEmpty()) {

           // if(EmailValidator.getInstance().isValid(emailField.getText())){
//            PasswordHash passwordHash = new PasswordHash();
//            String password = registerPassword.getText();
//            password = passwordHash.passwordHash2(password);





            wrongRegistration.setText("asdasad");
        }
        else{
            wrongRegistration.setText("Wypełnij wszystkie pola!");
        }

    }

}
