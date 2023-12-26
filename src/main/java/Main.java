import handlers.WishlistTelegramBot;
import lombok.extern.slf4j.Slf4j;
import model.db.DBConnection;
import model.db.DBManager;
import model.entity.MovieEntity;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import repository.MoviesRepo;
import repository.HistoryRepo;
import repository.UserRepo;
import repository.impl.MoviesRepoImpl;
import repository.impl.UserRepoImpl;
import utils.CookiesUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ServiceLoader;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        var dbmanager = DBManager.getInstance();
        
        HistoryRepo historyRepo = dbmanager.getHistoryRepo();
        UserRepo ur = new UserRepoImpl(dbmanager.getConnection());
        MoviesRepo mr = new MoviesRepoImpl(dbmanager.getConnection());
        
        
        
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            WishlistTelegramBot bot = new WishlistTelegramBot();
            telegramBotsApi.registerBot(bot);
            bot.init();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        
    }
}
