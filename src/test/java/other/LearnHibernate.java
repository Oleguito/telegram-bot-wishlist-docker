package other;

import model.entity.MovieEntity;
import model.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.telegram.telegrambots.meta.api.objects.User;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LearnHibernate {
    
    private static SessionFactory sessionFactory;
    private Session session;
    
    @BeforeAll
    static void beforeAll() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @BeforeEach
    void beforeEach() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }
    
    @AfterEach
    void afterEach() {
        session.close();
    }
    
    @Test
    @DisplayName("save user without movies")
    void save_user_without_movies() {
        int chatId = 1215125;
        UserEntity user = UserEntity.builder()
                .id(chatId)
                .username("TEST")
                .build();
        
        session.save(user);
        session.getTransaction().commit();
        
        UserEntity found = session.find(UserEntity.class, chatId);
        
        assertEquals(user, found);
    }
    
    @Test
    @DisplayName("save movie")
    void save_movie() {
        var movie = MovieEntity.builder()
                .year(9888)
                .title("blasakdhskdhf")
                .build();
        session.save(movie);
        session.getTransaction().commit();
        MovieEntity found = session.find(MovieEntity.class, movie.getId());
       
        assertEquals(movie, found);
    }
    
    @Test
    @DisplayName("save user with movies")
    void save_user_with_movies() {
        int chatId = 123;
        List <MovieEntity> movies = new ArrayList <>();
        movies.add(MovieEntity.builder()
                        .title("test movie")
                        .year(9999)
                .build());
        UserEntity user = UserEntity.builder()
                .id(chatId)
                .username("TEST")
                .movies(movies)
                .build();
        
        session.save(user);
        session.getTransaction().commit();
        
        UserEntity found = session.find(UserEntity.class, chatId);
        
        assertEquals(user, found);
    }

    @Test
    @DisplayName("save movie with users")
    void save_movie_with_users(){
        int chatId = 12345;
        
        List<UserEntity> users = new ArrayList <>();
        UserEntity user = UserEntity.builder()
                .id(chatId)
                .username("TEST1")
                .build();
        users.add(user);

        MovieEntity movie = MovieEntity.builder()
                        .title("test movie 11111")
                        .users(users)
                        .year(11111)
                        .build();


        session.save(movie);
        session.getTransaction().commit();

        MovieEntity found = session.find(MovieEntity.class, movie.getId());

        assertEquals(movie, found);
    }

    
}
