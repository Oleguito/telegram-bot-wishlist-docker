import handlers.WishlistTelegramBot;
import lombok.extern.slf4j.Slf4j;
import model.entity.HistoryEntity;
import model.entity.UserEntity;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import service.HistoryService;
import service.impl.HistoryServiceImpl;
import service.impl.UserServiceImpl;

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
//        var dbmanager = DBManager.getInstance();
//
//        HistoryRepo historyRepo = dbmanager.getHistoryRepo();
//        UserRepo ur = new UserRepoImpl(dbmanager.getConnection());
//        MoviesRepo mr = new MoviesRepoImpl(dbmanager.getConnection());
//
//
//        var service = new MoviesServiceImpl();
//        service.saveMovie(MovieEntityStub.getNewMovieEntityStub(1), 646014498L);
        // service.saveMovie(MovieEntityStub.getNewMovieEntityStub(2), 1337L);

        // service.deleteAllMoviesOfUserFromDatabase(1337L);

        WishlistTelegramBot bot = new WishlistTelegramBot();
        LongPollingBot botProxy = createBotProxy(bot);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(botProxy);
            bot.init();
        } catch (Exception e) {
            throw new RuntimeException("Телеграм бот API в main()");
        }

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
        HistoryServiceImpl.getInstance().insert(HistoryEntity.builder()
                .user_id(chatID)
                .command(callData)
                .operation_time(Timestamp.from(Instant.now()))
                .build());
    }
}
