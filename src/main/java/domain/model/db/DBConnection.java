package domain.model.db;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

@Getter
@Slf4j
public class DBConnection {
    
    private static DBConnection instance = null;
    private Connection connection;

    private DBConnection() {
        
        Dotenv dotenv = Dotenv.load();
        try {
            connection = DriverManager.getConnection(
                    dotenv.get("DB_URL"),
                    dotenv.get("DB_USERNAME"),
                    dotenv.get("DB_PASSWORD"));
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при подключении к БД. Подробности: " + e.getMessage());
        }
    }
    
    public static DBConnection getInstance() {
        if(instance == null) {
            return new DBConnection();
        }
        return instance;
    }
    
}
