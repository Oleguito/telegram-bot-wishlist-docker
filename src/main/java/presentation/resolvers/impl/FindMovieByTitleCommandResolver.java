package presentation.resolvers.impl;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import presentation.resolvers.CommandResolver;
import application.service.MoviesService;
import application.service.impl.MoviesServiceImpl;
import application.service.sessions.SessionManager;
import application.service.statemachine.State;
import infrastructure.utils.TelegramBotUtils;

import java.util.stream.Collectors;

public class FindMovieByTitleCommandResolver implements CommandResolver {

    private final String COMMAND_NAME = "/findbytitle";
    private final MoviesService moviesService;

    public FindMovieByTitleCommandResolver() {
        this.moviesService = new MoviesServiceImpl();
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        if(text.startsWith("/findbytitle")) {
            
            TelegramBotUtils.sendMessage(tg_bot, "Введите название фильма", chat_id);
            setSessionStateForThisUser(chat_id, State.FIND_BY_TITLE);
            
        } else {
            var movies = moviesService.getMovies(chat_id).stream()
                    .filter(
                            e -> e.getTitle().toLowerCase().contains(text.toLowerCase())
                    )
                    .toList();
            if(movies.isEmpty()) {
                TelegramBotUtils.sendMessage(tg_bot, """
                        Ничего не нашлось... 😞 Может, ачипятка??)
                        """,chat_id);
                TelegramBotUtils.sendMessage(tg_bot, """
                        Го еще разок!
                        """,chat_id);
                return;
            }
            var to = movies.stream()
                    .map(e -> e.toString())
                    .collect(Collectors.joining("\n"));
            TelegramBotUtils.sendMessage(tg_bot,to,chat_id);
            setSessionStateForThisUser(chat_id, State.IDLE);
        }
    }
    
    private static void setSessionStateForThisUser(Long chat_id, State state) {
        SessionManager.getInstance().getSession(chat_id).setState(state);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
