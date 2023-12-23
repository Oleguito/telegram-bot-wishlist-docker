package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    
    private static DBConnection instance = null;

    private Connection connection;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://83.147.246.87:5432/postgres",
                    "pavelioleg_user", "12345");
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при подключении к БД. Подробности: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }


    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
