package repository.impl;

import model.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.objects.User;
import repository.UserRepo;
import utils.SQLUtils;
import utils.SessionFactoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepoImpl implements UserRepo {

    private final Connection connection;
    private final SessionFactory sessionFactory = SessionFactoryImpl.getInstance();
    
    
    public UserRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        
        Session session = sessionFactory.openSession();
        
        UserEntity user = session.find(UserEntity.class, userEntity.getId());
        
        if(user == null) {
            session.beginTransaction();
            session.persist(userEntity);
            session.getTransaction().commit();
        }
        session.close();
        
        
        
        
        // try {
        //     String query = "call save_user(?, ?)";
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, userEntity.getId());
        //     preparedStatement.setString(2, userEntity.getUsername());
        //     preparedStatement.execute();
        //
        //     preparedStatement.close();
        // } catch (SQLException e) {
        //     throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        // }
    }

    public UserEntity getUser(Long chatID) {
        // try {
        //     String query = "select id, username from users u where u.id = ? limit 1";
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, chatID);
        //     ResultSet resultSet = preparedStatement.executeQuery();
        //
        //     resultSet.next();
        //     return UserEntity.builder()
        //             .id(resultSet.getLong(1))
        //             .username(resultSet.getString(2))
        //             .build();
        //
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        
        
        Session session = sessionFactory.openSession();
        var result = session.find(UserEntity.class, chatID);
        session.close();
        return  result;

    }

    @Override
    public Optional<String> getUsername(long chatId) {
        // try {
        //     String query = "select * from users where id = ? limit 1";
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, userId);
        //     var resultSet = preparedStatement.executeQuery();
        //     if (!resultSet.isBeforeFirst()) {
        //         return Optional.empty();
        //     }
        //     resultSet.next();
        //     Optional<String> username = Optional.ofNullable(resultSet.getString("username"));
        //
        //     resultSet.close();
        //     preparedStatement.close();
        //
        //     return username;
        // } catch (SQLException e) {
        //     throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        // }
        Session session = sessionFactory.openSession();
        var result =  Optional.of(session.find(UserEntity.class,  chatId).getUsername());
        session.close();
        return result;
    }
}
