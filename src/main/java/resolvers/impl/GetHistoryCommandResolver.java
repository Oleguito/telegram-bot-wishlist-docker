package resolvers.impl;

import model.db.DBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parsers.ParserRepo;
import parsers.impl.ParserRepoImpl;
import repository.HistoryRepo;
import repository.MoviesRepo;
import resolvers.CommandResolver;
import service.sessions.SessionManager;
import service.statemachine.State;
import utils.TelegramBotUtils;

import java.util.stream.Collectors;

public class GetHistoryCommandResolver implements CommandResolver {
    
    private final HistoryRepo historyRepo;
    
    public GetHistoryCommandResolver() {
        historyRepo = DBManager.getInstance().getHistoryRepo();
    }
    
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        if(text.startsWith("/history")) {
            var to = historyRepo.select(chat_id)
                    .stream()
                    .map(e -> e.toString())
                    .collect(Collectors.joining("\n"));
                    
            TelegramBotUtils.sendMessage(tg_bot,to,chat_id);
            SessionManager.getInstance().getSession(chat_id).setState(State.IDLE);
        }
    }

}
