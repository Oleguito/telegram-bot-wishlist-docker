package utils;

import model.db.DBConnection;
import model.entity.HistoryEntity;
import model.entity.MovieEntity;
import model.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryImpl {
    
    private static SessionFactory instance;
    
    public static SessionFactory getInstance() {
        if(instance == null) {
            instance = new Configuration()
                    .addAnnotatedClass(UserEntity.class)
                    .addAnnotatedClass(MovieEntity.class)
                    .addAnnotatedClass(HistoryEntity.class)
                    .configure().buildSessionFactory();
        }
        return instance;
    }
    
    
}
