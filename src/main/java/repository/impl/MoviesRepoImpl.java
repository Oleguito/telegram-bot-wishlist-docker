package repository.impl;

import model.entity.MovieEntity;
import repository.MoviesRepo;
import utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public List<MovieEntity> getMovies(Long chatID) {
        try {

            List<MovieEntity> result = new ArrayList <>();

            String query = """
                    select *
                    from movies
                    join users_movies on movies.id = users_movies.movie_id
                    where user_id = ?
            """;
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, chatID);
            var resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()) {
                return Collections.emptyList();
            }

            while(resultSet.next()) {
                result.add(MovieEntity.builder()
                        .id(resultSet.getLong("id"))
                        .ref(resultSet.getString("ref"))
                        .title(resultSet.getString("title"))
                        .year(resultSet.getInt("year"))
                        .build());
            }

            preparedStatement.close();
            resultSet.close();

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        }
    }
}
