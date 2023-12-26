package resolvers.impl;

import model.db.DBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import resolvers.CommandResolver;
import service.sessions.SessionManager;
import service.statemachine.State;
import utils.TelegramBotUtils;

import java.util.stream.Collectors;

public class ShowAllAddedMoviesCommandResolver implements CommandResolver {

    private final String COMMAND_NAME = "/showall";

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        if(text.startsWith("/showall")) {
            var movies = DBManager.getInstance().getMoviesRepo().getMovies(chat_id);
            var to = movies.stream()
                    .map(e -> e.toString())
                    .collect(Collectors.joining("\n"));
            TelegramBotUtils.sendMessage(tg_bot,to,chat_id);
            SessionManager.getInstance().getSession(chat_id).setState(State.IDLE);
        }
    }
}
