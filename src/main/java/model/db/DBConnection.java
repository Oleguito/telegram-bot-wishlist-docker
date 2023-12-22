package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://83.147.246.87:5432/postgres",
                    "pavelioleg_user", "12345");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при подключении к БД");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private static DBConnection instance = null;

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public void executeQuery() {
//        ResultSet resultSet = null;
//        try {
////            resultSet = connection.prepareStatement("select username, title from users u join country c on u.country_id = c.id").executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(resultSet);
    }

}
