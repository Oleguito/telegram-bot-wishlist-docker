package repository;

import model.entity.HistoryEntity;


import java.util.List;

public interface HistoryRepo {
    void insert(HistoryEntity historyEntity);
    
    void clear();
    
    List <HistoryEntity> select(long chatID);
}
