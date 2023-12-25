package repository.impl;

import model.entity.MovieEntity;
import repository.MoviesRepo;
import utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MoviesRepoImpl implements MoviesRepo {
    
    private final Connection connection;
    
    public MoviesRepoImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void saveMovie(MovieEntity movieEntity, Long chat_id) {
        try {
//            String query = "insert into movies (id, ref, title, year) values (?, ?, ?, ?)";
            String query = "call save_film(?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, movieEntity.getId());
            preparedStatement.setString(2, movieEntity.getRef());
            preparedStatement.setString(3, movieEntity.getTitle());
            preparedStatement.setInt(4, movieEntity.getYear());
            preparedStatement.setLong(5, chat_id);
            preparedStatement.execute();
            
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
    }
}
