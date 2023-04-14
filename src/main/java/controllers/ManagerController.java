package controllers;

import com.example.system.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ManagerController {

    @FXML
    private Button logoutButton;

    //Wylogowanie się użytkownika
    public void userLogout(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/main.fxml");
    }
}
