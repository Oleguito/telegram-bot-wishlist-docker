package resolvers.impl;

import model.db.DBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import resolvers.CommandResolver;
import service.sessions.SessionManager;
import service.statemachine.State;
import utils.TelegramBotUtils;

import java.util.stream.Collectors;

public class FindMovieByTitleCommandResolver implements CommandResolver {

    private final String COMMAND_NAME = "/findbytitle";

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        if(text.startsWith("/findbytitle")) {
            
            TelegramBotUtils.sendMessage(tg_bot, "Введите название фильма", chat_id);
            SessionManager.getInstance().getSession(chat_id).setState(State.FIND_BY_TITLE);
            
        } else {
            var movies = DBManager.getInstance().getMoviesRepo().getMovies(chat_id);
            var to = movies.stream()
                    .filter(e -> e.getTitle().contains(text))
                    .map(e -> e.toString())
                    .collect(Collectors.joining("\n"));
            TelegramBotUtils.sendMessage(tg_bot,to,chat_id);
            SessionManager.getInstance().getSession(chat_id).setState(State.IDLE);
        }
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
