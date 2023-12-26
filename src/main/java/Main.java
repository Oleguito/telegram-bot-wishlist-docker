import handlers.WishlistTelegramBot;
import model.db.DBManager;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import repository.MoviesRepo;
import repository.HistoryRepo;
import repository.UserRepo;
import repository.impl.MoviesRepoImpl;
import repository.impl.UserRepoImpl;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var dbmanager = DBManager.getInstance();
        
        HistoryRepo historyRepo = dbmanager.getHistoryRepo();
        UserRepo ur = new UserRepoImpl(dbmanager.getConnection());
        MoviesRepo mr = new MoviesRepoImpl(dbmanager.getConnection());
        
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
