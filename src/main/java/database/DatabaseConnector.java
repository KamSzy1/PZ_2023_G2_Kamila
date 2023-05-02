package database;

import java.sql.*;

public class DatabaseConnector {

    private final static String url = "jdbc:mysql://localhost:3306/firma";
    private final static String user = "root";
    private final static String pass = "";

    public static Connection connect() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
