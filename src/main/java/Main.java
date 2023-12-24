import handlers.WishlistTelegramBot;
import model.db.DBManager;
import model.entity.HistoryEntity;
import model.entity.MovieEntity;
import model.entity.UserEntity;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import repository.MoviesRepo;
import repository.ParserRepo;
import repository.HistoryRepo;
import repository.UserRepo;
import repository.impl.HistoryRepoImpl;
import repository.impl.MoviesRepoImpl;
import repository.impl.ParserRepoImpl;
import repository.impl.UserRepoImpl;

import java.util.List;

import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
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
        
        
        
        
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new WishlistTelegramBot());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        
    }
}
