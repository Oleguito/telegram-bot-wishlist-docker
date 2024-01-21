package repository;

import model.entity.HistoryEntity;


import javax.validation.constraints.NotNull;
import java.util.List;

public interface HistoryRepo {
    void insert(HistoryEntity historyEntity);
    
    void clearAllHistoryForUser(@NotNull Long userId);

    List<HistoryEntity> select(@NotNull Long userID);
}
