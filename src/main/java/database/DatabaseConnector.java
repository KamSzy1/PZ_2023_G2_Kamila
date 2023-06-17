package database;

import java.sql.*;

/**
 * Klasa do zarządzania połączeniem z bazą danych
 */
public class DatabaseConnector {

    /**
     * @param url URL do bazy danych
     * @param user Nazwa użytkownika w bazie
     * @param pass Hasło do bazy danych
     */
    private final static String url = "jdbc:mysql://localhost:3306/firma";
    private final static String user = "root";
    private final static String pass = "";

    /**
     * Metoda do połączenia z bazą danych
     *
     * @return Połączenie z bazą danych
     * @throws SQLException
     */
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(url, user, pass);
    }
}
