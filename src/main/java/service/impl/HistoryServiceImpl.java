package service.impl;

import model.db.DBConnection;
import model.db.DBManager;
import model.entity.HistoryEntity;
import repository.HistoryRepo;
import service.HistoryService;

import java.util.List;

public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepo historyRepo;

    public HistoryServiceImpl() {
        this.historyRepo = DBManager.getInstance().getHistoryRepo();
    }

    @Override
    public void insert(HistoryEntity historyEntity) {
        historyRepo.insert(historyEntity);
    }

    @Override
    public void clear(Long user_id) {
        historyRepo.clear(user_id);
    }

    @Override
    public List<HistoryEntity> getUserHistory(Long user_id) {
        return historyRepo.select(user_id);
    }
}
