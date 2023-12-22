package repository;

import model.entity.HistoryEntity;

public interface HistoryRepo {

    public void saveOperation(HistoryEntity historyEntity);
    public void getOperations(int user_id);

}
