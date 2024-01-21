package repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import model.entity.MovieEntity;
import model.entity.UserEntity;
import org.hibernate.SessionFactory;
import repository.UsersMoviesRepo;
import utils.SQLUtils;
import utils.SessionFactoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersMoviesRepoImpl implements UsersMoviesRepo {
    
    private final Connection connection;
    private final SessionFactory sessionFactory = SessionFactoryImpl.getInstance();
    
    public UsersMoviesRepoImpl(Connection connection) {
        this.connection = connection;
    }
    
    
    @Override
    public boolean movieRegistered(MovieEntity movie, Long chatID) {
        
        CriteriaBuilder criteriaBuilder = sessionFactory.openSession().getCriteriaBuilder();
        CriteriaQuery <UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root <UserEntity> root = criteriaQuery.from(UserEntity.class);
        // criteriaQuery.select(root).where(criteriaBuilder.equal(
        //         root.get("user").
        // ))
        
        Join <MovieEntity, UserEntity> moviesJoin = root.join("users");
        CriteriaQuery <UserEntity> result = criteriaQuery.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("chatId"), chatID),
                criteriaBuilder.equal(moviesJoin.get("id"), movie.getId())
        ));
        
        return false;
        
        // var result = false;
        // try {
        //     // String query = "insert into movies (id, ref, title, year) values (?, ?, ?, ?)";
        //     String query = "SELECT film_is_registered_by_user( ?, ? )";
        //
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, movie.getId());
        //     preparedStatement.setLong(2, chatID);
        //     var resultSet = preparedStatement.executeQuery();
        //     resultSet.next();
        //
        //     result = resultSet.getBoolean(1);
        //
        //     preparedStatement.close();
        //
        // } catch (SQLException e) {
        //     throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        // }
        //  return result;
    }
    
    @Override
    public void deleteMoviesOfUser(Long chatId) {
        try {
            
            String query = """
                    delete from users_movies
                    where user_id = ?
                    """;
            
            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
            preparedStatement.setLong(1, chatId);
            preparedStatement.execute();
            
            preparedStatement.close();
            
        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка выполнения запроса", e);
        }
        
    }
}
