// package other;

// import jakarta.persistence.criteria.CriteriaBuilder;
// import jakarta.persistence.criteria.CriteriaQuery;
// import jakarta.persistence.criteria.Root;
// import domain.model.entity.HistoryEntity;
// import domain.model.entity.MovieEntity;
// import domain.model.entity.UserEntity;
// import org.hibernate.Session;
// import org.hibernate.SessionFactory;
// import org.junit.jupiter.api.*;
// import infrastructure.utils.HibernateUtil;

// import java.sql.Timestamp;
// import java.time.Instant;
// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// public class LearnHibernateNew {
    
//     private static SessionFactory sessionFactory;
//     private Session session;
    
//     @BeforeAll
//     static void beforeAll() {
//         sessionFactory = HibernateUtil.getSessionFactory();
//     }
    
//     @BeforeEach
//     void beforeEach() {
//         session = sessionFactory.openSession();
//         session.beginTransaction();
//     }
    
//     @AfterEach
//     void afterEach() {
//         session.close();
//     }
    
//     @Test
//     @DisplayName("save user without movies")
//     void save_user_without_movies() {
//         int chatId = 1215125;
//         UserEntity user = UserEntity.builder()
//                 .id(chatId)
//                 .username("TEST")
//                 .build();
        
//         session.persist(user);
//         session.getTransaction().commit();
        
//         UserEntity found = session.find(UserEntity.class, chatId);
        
//         assertEquals(user, found);
//     }
    
//     @Test
//     @DisplayName("save movie")
//     void save_movie() {
//         var movie = MovieEntity.builder()
//                 .year(9888)
//                 .title("blasakdhskdhf")
//                 .build();
//         session.save(movie);
//         session.getTransaction().commit();
//         MovieEntity found = session.find(MovieEntity.class, movie.getId());
       
//         assertEquals(movie, found);
//     }
    
//     @Test
//     @DisplayName("save user with movies")
//     void save_user_with_movies() {
//         int chatId = 123;
//         List <MovieEntity> movies = new ArrayList <>();
//         movies.add(MovieEntity.builder()
//                         .title("test movie")
//                         .year(9999)
//                 .build());
//         UserEntity user = UserEntity.builder()
//                 .id(chatId)
//                 .username("TEST")
//                 .movies(movies)
//                 .build();
        
//         session.save(user);
//         session.getTransaction().commit();
        
//         UserEntity found = session.find(UserEntity.class, chatId);
        
//         assertEquals(user, found);
//     }

//     @Test
//     @DisplayName("save movie with users")
//     void save_movie_with_users(){
//         int chatId = 12345;
        
//         List<UserEntity> users = new ArrayList <>();
//         UserEntity user = UserEntity.builder()
//                 .id(chatId)
//                 .username("TEST1")
//                 .build();
//         users.add(user);

//         MovieEntity movie = MovieEntity.builder()
//                         .title("test movie 11111")
//                         .users(users)
//                         .year(11111)
//                         .build();


//         session.save(movie);
//         session.getTransaction().commit();

//         MovieEntity found = session.find(MovieEntity.class, movie.getId());

//         assertEquals(movie, found);
//     }
    
//     @Test
//     @DisplayName("save history entity")
//     void save_history_entity() {
//         HistoryEntity historyEntity = HistoryEntity.builder()
//                 .command("/testcommand")
//                 .operationTime(Timestamp.from(Instant.now()))
//                 .build();

//         session.save(historyEntity);
//         session.getTransaction().commit();

//         HistoryEntity found = session.find(HistoryEntity.class, historyEntity.getId());

//         assertEquals(historyEntity, found);
//     }
    
//     @Test
//     @DisplayName("save a user with a history entity")
//     void save_a_user_with_a_history_entity() {
        
//         List<HistoryEntity> historyEntities = new ArrayList <>();
//         HistoryEntity historyEntity = HistoryEntity.builder()
//                 .command("/testcommand")
//                 .operationTime(Timestamp.from(Instant.now()))
//                 .build();
//         historyEntities.add(historyEntity);
        
//         UserEntity user = UserEntity.builder()
//                 .username("vasya_pUpkin")
//                 .history(historyEntities)
//                 .id(90001)
//                 .build();

//         session.save(user);
//         session.getTransaction().commit();

//         UserEntity found = session.find(UserEntity.class, user.getId());

//         assertEquals(user, found);
//     }
    
//     @Test
//     @DisplayName("save a history entity with a user")
//     void save_a_history_entity_with_a_user() {
        
//         UserEntity user = UserEntity.builder()
//                 .username("vasya_pOpkin")
//                 .id(90002)
//                 .build();
        
//         HistoryEntity historyEntity = HistoryEntity.builder()
//                 .command("/anothertestcommand")
//                 .user(user)
//                 .operationTime(Timestamp.from(Instant.now()))
//                 .build();
        
//         List<HistoryEntity> historyEntities = new ArrayList <>();
//         historyEntities.add(historyEntity);
//         user.setHistory(historyEntities);
        
//         session.save(historyEntity);
//         session.getTransaction().commit();

//         HistoryEntity found = session.find(HistoryEntity.class, historyEntity.getId());

//         assertEquals(historyEntity, found);
//     }

//     @Test
//     @DisplayName("learn criteria builder")
//     void learn_criteria_builder() {
        
//         //region Сетап: сохранение юзера с фильмом в БД
//         MovieEntity movie = MovieEntity.builder()
//                 .title("Iron Man Ressurrection")
//                 .ref("reference")
//                 .year(9991)
//                 .build();
        
//         UserEntity user = UserEntity.builder()
//                 .username("baba_fedora")
//                 .id(1388)
//                 .build();
        
//         List <MovieEntity> userMovies = user.getMovies();
//         userMovies.add(movie);
        
//         session.save(user);
//         session.getTransaction().commit();
//         //endregion
        
//         Session anotherSession = sessionFactory.openSession();
//         CriteriaBuilder cb = anotherSession.getCriteriaBuilder();
//         CriteriaQuery <UserEntity> criteriaQuery = cb.createQuery(UserEntity.class);
//         Root <UserEntity> userEntity = criteriaQuery.from(UserEntity.class);
//         userEntity.join("movies");
        
//         // CriteriaQuery <UserEntity> result = criteriaQuery
//         //         .select(userEntity)
//         //         .where(cb.and(
//         //                         cb.equal(userEntity.get("id"),
//         //                                 me.get("id"))
//         //                 )
//         //         );
//         //
//         // anotherSession.createQuery(criteriaQuery)
        
//         // criteriaQuery.select(root).where(criteriaBuilder.equal(
//         //         root.get("user").
//         // ))
        
//         // Join <MovieEntity, UserEntity> moviesJoin = root.join("users");
//         // CriteriaQuery <UserEntity> result = criteriaQuery.select(root).where(criteriaBuilder.and(
//         //         criteriaBuilder.equal(root.get("chatId"), chatID),
//         //         criteriaBuilder.equal(moviesJoin.get("id"), movie.getId())
//         // ));
        
//         System.out.println("");
        
//     }
    
// }
