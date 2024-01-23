import handlers.WishlistTelegramBot;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import model.db.DBManager;
import model.entity.HistoryEntity;
import model.entity.MovieEntity;
import model.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cfg.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import repository.impl.MoviesRepoImpl;
import repository.impl.UsersMoviesRepoImpl;
import service.HistoryService;
import service.impl.HistoryServiceImpl;
import service.impl.UserServiceImpl;
import utils.SessionFactoryImpl;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;


@Slf4j
public class Main {

    private final HistoryService historyService = new HistoryServiceImpl();

    public static void main(String[] args) throws IOException {

        // StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().build();
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(MovieEntity.class)
                .addAnnotatedClass(HistoryEntity.class)
                .configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();

        MoviesRepoImpl moviesRepo = new MoviesRepoImpl(DBManager.getInstance().getConnection());
        UsersMoviesRepoImpl usersMoviesRepo = new UsersMoviesRepoImpl(DBManager.getInstance().getConnection());
        List<MovieEntity> movies = moviesRepo.getMovies(124121L);
        usersMoviesRepo.deleteMoviesOfUser(124121L);
        List<MovieEntity> moviesAfter = moviesRepo.getMovies(124121L);

//        UsersMoviesRepoImpl usersMoviesRepo = new UsersMoviesRepoImpl(DBManager.getInstance().getConnection());
//        usersMoviesRepo.movieRegistered(MovieEntity.builder()
//                        .id(121512L)
//                .build(), 12412L);

//        session.save(MovieEntity.builder()
//                .ref("afsfa")
//                .title("afsfa")
//                .year(1995)
//                .build());
//        MovieEntity movieEntity = session.find(MovieEntity.class, 1);
//
//        session.save(UserEntity.builder()
//                        .id(1215125)
//                        .username("TEST")
//                        .movies(List.of(movieEntity))
//                .build());
//
//        UserEntity userEntity = session.find(UserEntity.class, 1215125);
//
//        session.getTransaction().commit();

        return;

//        WishlistTelegramBot bot = new WishlistTelegramBot();
//        LongPollingBot botProxy = createBotProxy(bot);
//
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot(botProxy);
//            bot.init();
//        } catch (Exception e) {
//            throw new RuntimeException("Телеграм бот API в main()");
//        }

    }

    private static LongPollingBot createBotProxy(WishlistTelegramBot bot) {
        LongPollingBot botProxy = (LongPollingBot) Proxy.newProxyInstance(WishlistTelegramBot.class.getClassLoader(),
                WishlistTelegramBot.class.getSuperclass().getInterfaces(),
                (proxy, method, args1) -> {
                    if (method.getName().equals("onUpdatesReceived")) {
                        List<Update> updates = (List<Update>) args1[0];
                        for (Update update : updates) {
                            if (update.hasCallbackQuery()) {
                                var query = update.getCallbackQuery();

                                String callData = query.getData();
                                Long chatID = query.getMessage().getChatId();
                                String username = update.getCallbackQuery().getFrom().getUserName();

                                saveUser(chatID, username);
                                saveHistory(chatID, callData);

                            }

                            if (update.hasMessage()) {

                                var message = update.getMessage();

                                if (message.hasText()) {
                                    var text = message.getText();
                                    var chatID = message.getChatId();

                                    String username = message.getChat().getUserName();
                                    saveUser(chatID, username);
                                    saveHistory(chatID, text);

                                }
                            }
                        }
                    }
                    return method.invoke(bot, args1);
                });
        return botProxy;
    }

    private static void saveUser(Long chatID, String username) {
        UserServiceImpl.getInstance().saveUser(UserEntity.builder()
                .id(chatID)
                .username(username)
                .build());
    }

    private static void saveHistory(Long chatID, String callData) {
        UserEntity user = UserServiceImpl.getInstance().getUser(chatID);

        if (user == null) {
            throw new EntityNotFoundException("Такого пользователя нет в БД");
        }

        HistoryServiceImpl.getInstance().insert(HistoryEntity.builder()
                .user(user)
                .command(callData)
                .operationTime(Timestamp.from(Instant.now()))
                .build());
    }
}
