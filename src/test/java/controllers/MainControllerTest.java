package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainControllerTest {

    private MainController mainController;

    @BeforeEach
    public void setUp(){
        mainController = new MainController();
    }
    @Test
    public void loginTest(){
        String email = "";
        String password = "sdada";

        assertThrows(Exception.class, () -> mainController.login(email, password));
    }

    @Test
    public void getUserFromTokenTest(){
        String token = "";
        assertThrows(Exception.class, () -> mainController.getUserFromToken(token));
    }

    @Test
    public void changePasswordTest(){
        String email = "";
        String password = "sdada";
        String repeatedPassword = "sdada";

        assertThrows(Exception.class, () -> mainController.changePassword(email, password, repeatedPassword));
    }

}
