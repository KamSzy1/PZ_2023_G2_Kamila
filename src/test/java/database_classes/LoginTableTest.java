package database_classes;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class LoginTableTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testConnection() throws SQLException {
// Arrange
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/firma";
        String user = "root";
        String password = "";

// Act
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        assertNotNull(conn);
        assertTrue(conn.isValid(1));

    }
}
