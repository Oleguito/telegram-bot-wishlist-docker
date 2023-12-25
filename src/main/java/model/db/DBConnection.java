package model.db;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.SLF4JServiceProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ServiceLoader;

@Getter
public class DBConnection {
    
    private static DBConnection instance = null;
    private Logger logger = LoggerFactory.getLogger(DBConnection.class);
    
    @Getter
    private Connection connection;

    private DBConnection() {
        
        logger.info("Подключились к базе данных");
        try {
            connection = DriverManager.getConnection(System.getenv("DB_URL"),
                    "pavelioleg_user", "12345");
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
