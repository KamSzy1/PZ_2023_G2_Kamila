package validate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateTaskTest {

    @Test
    public void titleTest(){
        String title = "Tytuł ważnego zadania";
        String titleWrong = "";

        assertDoesNotThrow(() -> ValidateTask.checkTitle(title));
        assertThrows(Exception.class, () -> ValidateTask.checkTitle(titleWrong));
    }

    @Test
    public void descriptionTest(){
        String desc = "Opis bardzo ważnego zadania";
        String descWrong = "";

        assertDoesNotThrow(() -> ValidateTask.checkDescription(desc));
        assertThrows(Exception.class, () -> ValidateTask.checkDescription(descWrong));
    }

}
