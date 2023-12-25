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
import parsers.impl.ParserRepoImpl;
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

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        var dbmanager = DBManager.getInstance();
        
        HistoryRepo historyRepo = dbmanager.getHistoryRepo();
        UserRepo ur = new UserRepoImpl(dbmanager.getConnection());
        MoviesRepo mr = new MoviesRepoImpl(dbmanager.getConnection());
        
        
        
        
        // var res = mr.getMovies();
        
        // var sexyCookies = new CookiesUtils();
        
        
        // System.out.println("");
        // System.out.println(ur.getUsername(9876L));
        
        // /* https://www.kinopoisk.ru/film/476/ */
        //
        // mr.saveMovie(MovieEntity.builder()
        //         .id(476)
        //         .ref("https://www.kinopoisk.ru/film/476/")
        //         .title("Назад в будущее")
        //         .year(1985)
        //         .build());
        
        
        
        
        // var user = UserEntity.builder().id(9876).username("vasyli").build();
        // ur.saveUser(user);
        
        // List<HistoryEntity> userhistory = historyRepo.select(1324L);
        // userhistory.forEach(System.out::println);
        // var res = DBManager
        //         .getInstance()
        //         .getHistoryRepo()
        //         .select(1324L); // .forEach(System.out::println);
        
        
        //
        // HistoryEntity build = HistoryEntity.builder()
        //         .user_id(6226)
        //         .command("teest")
        //         .operation_time(Timestamp.from(Instant.now()))
        //         .build();
        //
        // historyRepo.insert(build);
        
        // ParserRepo parser = new ParserRepoImpl();
        // var movie = parser.parse("https://www.kinopoisk.ru/film/476/");
        
        // System.out.println("");

//        ParserRepoImpl parserRepo = new ParserRepoImpl();
//        parserRepo.parse("https://www.kinopoisk.ru/film/326/");


        // try {
        //     TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        //     WishlistTelegramBot bot = new WishlistTelegramBot();
        //     telegramBotsApi.registerBot(bot);
        //     bot.init();
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     throw new RuntimeException();
        // }
        
    }
}
