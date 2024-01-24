package infrastructure.repository.impl;

import domain.model.entity.MovieEntity;
import domain.model.entity.UserEntity;
import infrastructure.repository.MoviesRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import infrastructure.utils.SessionFactoryImpl;

import java.util.List;

public class MoviesRepoImpl implements MoviesRepo {
    
    // private final Connection connection;
    private final SessionFactory sessionFactory = SessionFactoryImpl.getInstance();

    // public MoviesRepoImpl(Connection connection) {
    //     this.connection = connection;
    // }
    
    @Override
    public void saveMovie(MovieEntity movieEntity, Long chatId) {

        Session session = sessionFactory.openSession();
        
        UserEntity currentUser = session.find(UserEntity.class, chatId);
        currentUser.getMovies().add(movieEntity);
        session.beginTransaction();
        session.merge(currentUser);
        session.getTransaction().commit();
        
        
        // UserEntity currentUser = session.find(UserEntity.class, chatId);
        // movieEntity.addUser(currentUser);
        //
        // session.beginTransaction();
        // /*  TODO: если написать persist то Hibernate ругается
        // *   что фильм не находится в контексте для сохранения
        // *   un.... entity passed to persist */
        // session.save(movieEntity);
        // session.getTransaction().commit();
        // session.close();
        //
        
        // try {
        //     String query = "call save_film(?, ?, ?, ?, ?)";
        //
        //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
        //     preparedStatement.setLong(1, movieEntity.getId());
        //     preparedStatement.setString(2, movieEntity.getRef());
        //     preparedStatement.setString(3, movieEntity.getTitle());
        //     preparedStatement.setInt(4, movieEntity.getYear());
        //     preparedStatement.setLong(5, chatId);
        //     preparedStatement.execute();
        //
        //     preparedStatement.close();
        //
        // } catch (SQLException e) {
        //     throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
        // }
    }

    @Override
    public List<MovieEntity> getMovies(Long chatID) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
        JpaCriteriaQuery<MovieEntity> query = cb.createQuery(MovieEntity.class);
        JpaRoot<MovieEntity> root = query.from(MovieEntity.class);

        query.select(root).where(cb.equal(root.get("users").get("id"), chatID));

        return session.createQuery(query).getResultList();

//        try {
//
//            List<MovieEntity> result = new ArrayList <>();
//
//            String query = """
//                    select *
//                    from movies
//                    join users_movies on movies.id = users_movies.movie_id
//                    where user_id = ?
//            """;
//            PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
//            preparedStatement.setLong(1, chatID);
//            var resultSet = preparedStatement.executeQuery();
//
//            if(!resultSet.isBeforeFirst()) {
//                return Collections.emptyList();
//            }
//
//            while(resultSet.next()) {
//                result.add(MovieEntity.builder()
//                        .id(resultSet.getLong("id"))
//                        .ref(resultSet.getString("ref"))
//                        .title(resultSet.getString("title"))
//                        .year(resultSet.getInt("year"))
//                        .build());
//            }
//
//            preparedStatement.close();
//            resultSet.close();
//
//            return result;
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Произошла ошибка выполнения запроса. Информация: " + e.getMessage());
//        }
    }
}
