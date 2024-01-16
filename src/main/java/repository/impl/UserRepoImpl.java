package repository.impl;

import model.entity.UserEntity;
import repository.UserRepo;
import utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepoImpl implements UserRepo {

    private final Connection connection;

    public UserRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        try {
            String query = "call save_user(?, ?)";
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, userEntity.getId());
            preparedStatement.setString(2, userEntity.getUsername());
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
    }

    public UserEntity getUser(Long chatID) {
        try {
            String query = "select id, username from users u where u.id = ? limit 1";
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, chatID);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            return UserEntity.builder()
                    .id(resultSet.getLong(1))
                    .username(resultSet.getString(2))
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Optional<String> getUsername(long userId) {
        try {
            String query = "select * from users where id = ? limit 1";
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, userId);
            var resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return Optional.empty();
            }
            resultSet.next();
            Optional<String> username = Optional.ofNullable(resultSet.getString("username"));

            resultSet.close();
            preparedStatement.close();

            return username;
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
    }
}
