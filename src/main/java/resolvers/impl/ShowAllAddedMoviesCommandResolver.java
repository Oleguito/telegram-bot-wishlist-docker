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
            
            DBManager.getInstance().getMoviesRepo().getMovies(chat_id)
                    .stream()
                    .forEach(e -> {
                        TelegramBotUtils.sendMessage(tg_bot,e.toString(),chat_id);
                    });
            SessionManager.getInstance().getSession(chat_id).setState(State.IDLE);
        }
    }
}
