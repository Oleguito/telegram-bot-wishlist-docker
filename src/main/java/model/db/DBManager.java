package model.db;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import repository.HistoryRepo;
import repository.MoviesRepo;
import parsers.Parser;
import repository.UserRepo;
import repository.UsersMoviesRepo;
import repository.impl.HistoryRepoImpl;
import repository.impl.MoviesRepoImpl;
import parsers.impl.KinopoiskParser;
import repository.impl.UserRepoImpl;
import repository.impl.UsersMoviesRepoImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class DBManager {
    
    private static DBManager instance = null;
    
    private final Connection connection;
    private final HistoryRepo historyRepo;
    private final MoviesRepo moviesRepo;
    private final Parser parser;
    private final UserRepo userRepo;
    private final UsersMoviesRepo usersMoviesRepo;
    
    private DBManager() {
        
        connection = DBConnection.getInstance().getConnection();
        historyRepo = new HistoryRepoImpl(connection);
        moviesRepo = new MoviesRepoImpl();
        usersMoviesRepo = new UsersMoviesRepoImpl(connection);
        userRepo = new UserRepoImpl(connection);
        parser = new KinopoiskParser();

    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
    
    public <T> List <T> selectFromDatabase(String databaseName, Class<T> type) {
        
        return new ArrayList <>();
    }
    
}
