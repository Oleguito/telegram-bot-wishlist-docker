package utils;


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
                    // .addAnnotatedClass(Country.class)
                    // .addAnnotatedClass(User.class)
                    // .addAnnotatedClass(Advertisement.class)
                    // .addAnnotatedClass(Languages.class)
                    .configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}