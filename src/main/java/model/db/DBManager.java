package model.db;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.impl.HistoryRepoImpl;

import java.sql.Connection;

@Getter
public class DBManager {

    private Logger logger = LoggerFactory.getLogger(DBManager.class);
//    private final AdvertisementRepository advertisementRepository;
    private final Connection connection;
    private final HistoryRepoImpl historyRepo;
    
    private DBManager() {
        logger.info("DBManager created");

        connection = DBConnection.getInstance().getConnection();
        this.historyRepo = new HistoryRepoImpl(connection);
//        advertisementRepository = new AdvertisementRepository(connection);
    }


    private static DBManager instance = null;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }

}
