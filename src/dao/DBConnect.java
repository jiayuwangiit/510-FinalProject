package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    protected Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private static String url = "jdbc:mysql://127.0.0.1:3306/510labs";
    private static String username = "root";
    private static String password = "";

    public DBConnect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error creating connection to database: " + e);
            System.exit(-1);
        }
    }
}
