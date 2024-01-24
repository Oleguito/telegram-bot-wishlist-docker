package infrastructure.utils;

import domain.model.entity.HistoryEntity;
import domain.model.entity.MovieEntity;
import domain.model.entity.UserEntity;
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
