package utils;


import model.entity.HistoryEntity;
import model.entity.MovieEntity;
import model.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author 1ommy
 * @version 07.01.2024
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(UserEntity.class)
                    .addAnnotatedClass(MovieEntity.class)
                    .addAnnotatedClass(HistoryEntity.class)
                    .configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}