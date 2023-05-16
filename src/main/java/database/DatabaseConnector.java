package database;

import java.sql.*;

public class DatabaseConnector {

    private final static String url = "jdbc:mysql://localhost:3306/firma";
    private final static String user = "root";
    private final static String pass = "";

    public static Connection connect() throws SQLException{

        Connection connection = DriverManager.getConnection(url, user, pass);
        System.out.println("Connected");

        return connection;
    }
}
