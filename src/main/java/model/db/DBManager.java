package model.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class DBManager {

    private Logger logger = LoggerFactory.getLogger(DBManager.class);
//    private final AdvertisementRepository advertisementRepository;

    private DBManager() {
        logger.info("DBManager created");

        Connection connection = DBConnection.getInstance().getConnection();

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
