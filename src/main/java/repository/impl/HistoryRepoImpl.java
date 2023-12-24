package repository.impl;

import model.entity.HistoryEntity;
import utils.SQLUtils;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setInt(1, historyEntity.getUser_id());
            preparedStatement.setString(2, historyEntity.getCommand());
            preparedStatement.setTimestamp(3, historyEntity.getOperation_time());
            preparedStatement.execute();
            
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
    }

    @Override
    public void clear(@NotNull Long userId) {
        try {
            String query = "delete from history where user_id = ?";
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, userId);
            preparedStatement.execute();
            
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
    }

    @Override
    public List<HistoryEntity> select(@NotNull Long userId) {
       List<HistoryEntity> historyEntities = new ArrayList<>();
       try {
            String query = "select * from history where user_id = ?";
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                historyEntities.add(HistoryEntity.builder()
                        .id(resultSet.getLong("id"))
                        .user_id(resultSet.getInt("user_id"))
                        .command(resultSet.getString("command"))
                        .operation_time(resultSet.getTimestamp("operation_time"))
                        .build());
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
       return historyEntities;
    }
}
