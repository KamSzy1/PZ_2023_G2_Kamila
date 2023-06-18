package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Klasa do zarządzaniami zapytaniami do bazy dancyh
 */
public class QExecutor {

    /**
     * Do wykonywania Selectów
     *
     * @param selectQuery Napisane zapytanie
     * @return Wynik z bazy danych
     */
    public static ResultSet executeSelect(String selectQuery) {
        try {
            Connection connection = DatabaseConnector.connect();
            Statement statement = connection.createStatement();
            return statement.executeQuery(selectQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Do wykonywania operacji w bazie danych (insert, delete)
     *
     * @param query Napisane zapytanie
     */
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
