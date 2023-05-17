package controllers;

import com.example.system.*;
import database.DatabaseConnector;
import database_classes.UsersTable;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class AddEmployeeControllerTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addUser() throws SQLException {
        // Tworzenie połączenia testowego z bazą danych
        Connection connection = DatabaseConnector.connect();



       UsersTable user = new UsersTable(22, "Lidia", "Pacynaaa", "Kolbuszowska 11", "36-122", "Dzikowiec", 530188983, 1,"haha", 1);


        String query =
                "INSERT INTO users (id_user, name, surname, address, zip, place, phone_num, position_id, token, groups)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, user.getIdUser());
        statement.setString(2, user.getName());
        statement.setString(3, user.getSurname());
        statement.setString(4, user.getAddress());
        statement.setString(5, user.getZip());
        statement.setString(6, user.getPlace());
        statement.setInt(7, user.getPhoneNumber());
        statement.setInt(8, user.getPositionId());
        statement.setString(9, user.getToken());
        statement.setInt(10, user.getGroups());
        statement.executeUpdate();


        // Sprawdzenie, czy auto zostało dodane do bazy danych
        query = "SELECT * FROM users WHERE id_user = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, user.getIdUser());
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());

    }
}
