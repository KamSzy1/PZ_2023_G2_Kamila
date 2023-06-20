package database;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectorTest {

    @Test
    public void testConnection() throws SQLException {
        assertNotNull(DatabaseConnector.connect());
        assertTrue(DatabaseConnector.connect().isValid(1));
    }

}
