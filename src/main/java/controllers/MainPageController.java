package controllers;

import com.example.system.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainPageController {

    @FXML
    Button loginButton;

    @FXML
    Button registrationButton;

    //Przejście do panelu logowania
    public void goToLogin(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/login.fxml");
    }

    //Przejście do panelu rejestracji
    public void goToRegistration(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/registration.fxml");
    }

}
