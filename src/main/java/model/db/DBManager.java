package model.db;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.HistoryRepo;
import repository.MoviesRepo;
import parsers.ParserRepo;
import repository.UserRepo;
import repository.impl.HistoryRepoImpl;
import repository.impl.MoviesRepoImpl;
import parsers.impl.ParserRepoImpl;
import repository.impl.UserRepoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class DBManager {
    
    private static DBManager instance = null;

    private Logger logger = LoggerFactory.getLogger(DBManager.class);
    private final Connection connection;
    private final HistoryRepo historyRepo;
    private final MoviesRepo moviesRepo;
    private final ParserRepo parserRepo;
    private final UserRepo userRepo;
    
    private DBManager() {
        
        connection = DBConnection.getInstance().getConnection();
        historyRepo = new HistoryRepoImpl(connection);
        moviesRepo = new MoviesRepoImpl(connection);
        userRepo = new UserRepoImpl(connection);
        parserRepo = new ParserRepoImpl();
        
        logger.info("DBManager created");
    }



    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
    
    
    public ResultSet executeSelect(String query) {
       ResultSet resultSet = null;
       try {
           /* "select username, title from users u join country c on u.country_id = c.id" */
           resultSet = connection.prepareStatement(query).executeQuery();
       } catch (SQLException e) {
           e.printStackTrace();
           throw new RuntimeException();
       }
       return resultSet;
    }
    
}
