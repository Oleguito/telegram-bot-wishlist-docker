import model.db.DBManager;
import model.entity.HistoryEntity;
import repository.impl.HistoryRepoImpl;

import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        var dbmanager = DBManager.getInstance();
        HistoryRepoImpl historyRepo = dbmanager.getHistoryRepo();

        HistoryEntity build = HistoryEntity.builder()
                .user_id(6226)
                .command("teest")
                .operation_time(Timestamp.from(Instant.now()))
                .build();

        historyRepo.insert(build);

    }
}
