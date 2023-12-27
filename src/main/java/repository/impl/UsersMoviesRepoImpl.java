package repository.impl;

import model.entity.MovieEntity;
import repository.UsersMoviesRepo;
import utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersMoviesRepoImpl implements UsersMoviesRepo {
    
    private final Connection connection;
    
    public UsersMoviesRepoImpl(Connection connection) {
        this.connection = connection;
    }
    
    
    @Override
    public boolean movieRegistered(MovieEntity movie, Long chatID) {
        var result = false;
        try {
            // String query = "insert into movies (id, ref, title, year) values (?, ?, ?, ?)";
            String query = "SELECT film_is_registered_by_user( ?, ? )";

            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, movie.getId());
            preparedStatement.setLong(2, chatID);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            
            result = resultSet.getBoolean(1);
            
            preparedStatement.close();
            
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
         return result;
    }
}
