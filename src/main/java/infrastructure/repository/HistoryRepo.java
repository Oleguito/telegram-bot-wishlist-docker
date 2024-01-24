package infrastructure.repository;

import domain.model.entity.HistoryEntity;


import javax.validation.constraints.NotNull;
import java.util.List;

public interface HistoryRepo {
    void insert(HistoryEntity historyEntity);
    
    void clearAllHistoryForUser(@NotNull Long userId);

    List<HistoryEntity> select(@NotNull Long userID);
}
