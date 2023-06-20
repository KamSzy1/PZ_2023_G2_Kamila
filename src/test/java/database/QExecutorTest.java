package database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QExecutorTest {

    @Test
    public void executeSelectTest() {
        ResultSet result = QExecutor.executeSelect("SELECT * FROM users");
        assertNotNull(result);
    }

    @Test
    public void executeQueryTest() {
        String result = "";
        Assertions.assertThrows(Exception.class, () -> QExecutor.executeQuery(result));
    }

}
