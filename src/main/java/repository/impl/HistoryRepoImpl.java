package repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.entity.HistoryEntity;
import model.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.SQLUtils;
import utils.SessionFactoryImpl;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepoImpl implements repository.HistoryRepo {

    private final Connection connection;
    private final SessionFactory sessionFactory = SessionFactoryImpl.getInstance();
    
    public HistoryRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(HistoryEntity historyEntity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(historyEntity);
        session.getTransaction().commit();
        session.close();
        // try {
        //     String query = "insert into history (user_id, command, operation_time) values (?, ?, ?)";
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, historyEntity.getUser().getId());
        //     preparedStatement.setString(2, historyEntity.getCommand());
        //     preparedStatement.setTimestamp(3, historyEntity.getOperationTime());
        //     preparedStatement.execute();
        //
        //     preparedStatement.close();
        // } catch (SQLException e) {
        //     throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        // }
    }

    @Override
    public void clearAllHistoryForUser(@NotNull Long chatId) {
        //
        // CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        //
        // CriteriaQuery <HistoryEntity> query = criteriaBuilder.createQuery(HistoryEntity.class);
        //
        // Root <HistoryEntity> from = query.from(HistoryEntity.class);
        //
        // query.
        
        Session session = sessionFactory.openSession();
        UserEntity user = session.find(UserEntity.class, chatId);
        user.getHistory().clear();
        session.merge(user);
        session.getTransaction().commit();
        session.close();
        
        
        
        // try {
        //     String query = "delete from history where user_id = ?";
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, userId);
        //     preparedStatement.execute();
        //
        //     preparedStatement.close();
        // } catch (SQLException e) {
        //     throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        // }
    }

    @Override
    public List<HistoryEntity> select(@NotNull Long chatId) {
        
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        UserEntity user = session.find(UserEntity.class, chatId);
        session.getTransaction().commit();
        session.close();
        List <HistoryEntity> result = user.getHistory();
        return result;
        
        
        // List<HistoryEntity> historyEntities = new ArrayList<>();
        // try {
        //     String query = "select h.id, h.user_id, h.command, h.operation_time, u.username from history h where user_id = ? join users u";
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, userId);
        //     ResultSet resultSet = preparedStatement.executeQuery();
        //     while (resultSet.next()) {
        //         historyEntities.add(HistoryEntity.builder()
        //                 .id(resultSet.getLong("id"))
        //                 .user(UserEntity.builder()
        //                         .id(resultSet.getLong(2))
        //                         .username(resultSet.getString(5))
        //                         .build())
        //                 .command(resultSet.getString("command"))
        //                 .operationTime(resultSet.getTimestamp("operation_time"))
        //                 .build());
        //     }
        //     resultSet.close();
        //     preparedStatement.close();
        // } catch (SQLException e) {
        //     throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        // }
        // return historyEntities;
    }
}
