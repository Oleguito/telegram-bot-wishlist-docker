package repository.impl;

import model.entity.HistoryEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class HistoryRepoImpl implements repository.HistoryRepo {

    private final Connection connection;

    public HistoryRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(HistoryEntity historyEntity) {
        try {
            String query = "insert into history (user_id, command, operation_time) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, historyEntity.getUser_id());
            preparedStatement.setString(2, historyEntity.getCommand());
            preparedStatement.setTimestamp(3, historyEntity.getOperation_time());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());        }

    }

    @Override
    public void clear() {

    }

    @Override
    public List<HistoryEntity> select(long chatID) {
        return null;
//        List<HistoryEntity> historyEntities = new ArrayList<>();
//
//        String query = "select "

    }
}
