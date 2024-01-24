package application.service.impl;

import application.service.HistoryService;
import domain.model.db.DBManager;
import domain.model.entity.HistoryEntity;
import infrastructure.repository.HistoryRepo;

import java.util.List;

public class HistoryServiceImpl implements HistoryService {

    private static HistoryService instance;
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
        historyRepo.clearAllHistoryForUser(user_id);
    }

    @Override
    public List<HistoryEntity> getUserHistory(Long user_id) {
        return historyRepo.select(user_id);
    }

    public static HistoryService getInstance() {
        if (instance == null) {
            instance = new HistoryServiceImpl();
        }
        return instance;
    }

}
