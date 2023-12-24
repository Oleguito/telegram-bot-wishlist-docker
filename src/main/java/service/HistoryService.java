package service;

import model.entity.HistoryEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface HistoryService {

    public void insert(HistoryEntity historyEntity);
    public void clear(@NotNull Long user_id);
    public List<HistoryEntity> getUserHistory(@NotNull Long user_id);

}
