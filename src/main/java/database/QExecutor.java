package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QExecutor {

    // To wykorzystujemy do typowych zapytań Select
    public static ResultSet executeSelect(String selectQuery) {
        try {
            Connection connection = DatabaseConnector.connect();
            Statement statement = connection.createStatement();
            return statement.executeQuery(selectQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //To wykorzystujemy do operacji na tabeli czyli inserty delety
    public static void executeQuery(String query) {
        try {
            Connection connection = DatabaseConnector.connect();
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
