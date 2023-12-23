package model.db;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.HistoryRepo;
import repository.MoviesRepo;
import repository.ParserRepo;
import repository.UserRepo;
import repository.impl.HistoryRepoImpl;
import repository.impl.MoviesRepoImpl;
import repository.impl.ParserRepoImpl;
import repository.impl.UserRepoImpl;

import java.sql.Connection;

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

}
