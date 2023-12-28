package resolvers.impl;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import resolvers.CommandResolver;
import service.MoviesService;
import service.impl.MoviesServiceImpl;
import utils.TelegramBotUtils;

public class ClearAllAddedMoviesCommandResolver implements CommandResolver {
    
    private final MoviesService moviesService;
    
    public ClearAllAddedMoviesCommandResolver() {
        moviesService = new MoviesServiceImpl();
    }
    
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String userText, Long chat_id) {
        if(userText.startsWith("/delete")) {
            moviesService.deleteAllMoviesOfUserFromDatabase(chat_id);
            
            TelegramBotUtils.sendImage(tg_bot,
                    "src/main/resources/mister-proper.png", chat_id);
            
            TelegramBotUtils.sendMessage(tg_bot, """
                    Чисто!
                    """, chat_id);
        }
    }
    
    @Override
    public String getCommandName() {
        return "/delete";
    }
}
