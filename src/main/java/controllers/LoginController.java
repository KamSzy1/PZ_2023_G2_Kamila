package controllers;

import com.example.system.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label wrongLogin;

    //userLogin jest podpięty pod przycisk
    public void userLogin(ActionEvent event) throws IOException {
        login();
    }

    //Metoda do logowania
    public void login() throws IOException{

        //To jest po to, aby zmienić scene/wyświetlany panel
        Main main = new Main();

        //Sprawdzanie pól emaila oraz hasła -> na razie wszystko na sztywno ustawione
        if(emailField.getText().equals("manager") && passwordField.getText().equals("123")){
            wrongLogin.setText("Zalogowano pomyślnie!");
            main.changeScene("/manager.fxml");
        } else if (emailField.getText().equals("admin") && passwordField.getText().equals("123")) {
            wrongLogin.setText("Zalogowano pomyślnie!");
            main.changeScene("/admin.fxml");
        } else if (emailField.getText().equals("employee") && passwordField.getText().equals("123")) {
            wrongLogin.setText("Zalogowano pomyślnie!");
            main.changeScene("/employee.fxml");
        } else if (emailField.getText().isEmpty() && emailField.getText().isEmpty()) {
            wrongLogin.setText("Uzupełnij wszystkie pola!");
        }
        else{
            wrongLogin.setText("Zły email bądź hasło");
        }

    }

    //Przycisk powrotu
    public void goBack(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/mainPage.fxml");
    }



}
