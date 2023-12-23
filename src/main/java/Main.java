import handlers.WishlistTelegramBot;
import model.db.DBManager;
import model.entity.HistoryEntity;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import repository.ParserRepo;
import repository.impl.HistoryRepoImpl;
import repository.impl.ParserRepoImpl;

import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        // var dbmanager = DBManager.getInstance();
        // HistoryRepoImpl historyRepo = dbmanager.getHistoryRepo();
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
        
        
        
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new WishlistTelegramBot());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        
    }
}
