package model.db;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.SLF4JServiceProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ServiceLoader;

@Getter
@Slf4j
public class DBConnection {
    
    private static DBConnection instance = null;
    private Connection connection;

    private DBConnection() {
        
        try {
            connection = DriverManager.getConnection(
                    System.getenv("DB_URL"),
                    System.getenv("DB_USERNAME"),
                    System.getenv("DB_PASSWORD"));
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при подключении к БД. Подробности: " + e.getMessage());
        }
        log.info("Подключились к БД");
    }
    
    public static DBConnection getInstance() {
        if(instance == null) {
            return new DBConnection();
        }
        return instance;
    }
    
}
