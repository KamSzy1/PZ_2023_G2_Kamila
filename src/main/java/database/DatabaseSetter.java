package database;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DatabaseSetter {

    public static void databaseSetter() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/firma";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            DriverManager.getConnection(url, "root", "");
        } catch (SQLException e) {
            String urlDatabase = "jdbc:mysql://localhost:3306/";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection con = DriverManager.getConnection(urlDatabase, "root", "");
            ScriptRunner sr = new ScriptRunner(con);
            Reader reader = new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("database.sql")), StandardCharsets.UTF_8);
            sr.runScript(reader);
        }
    }
}








