package controllers;


import com.example.system.Main;
import javafx.event.ActionEvent;

import java.io.IOException;

public class EmployeeController {

    //Wylogowanie się użytkownika
    public void userLogout(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/login.fxml");
    }
}
