package resolvers.impl;

import model.db.DBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import repository.HistoryRepo;
import resolvers.CommandResolver;
import service.sessions.SessionManager;
import service.statemachine.State;
import utils.TelegramBotUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetHistoryCommandResolver implements CommandResolver {
    
    private final HistoryRepo historyRepo;
    private final String COMMAND_NAME = "/history";

    public GetHistoryCommandResolver() {
        historyRepo = DBManager.getInstance().getHistoryRepo();
    }
    
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        if(text.startsWith("/history")) {
            var to = historyRepo.select(chat_id)
                    .stream()
                    .map(e -> e.toString())
                    .toList();
            
            generateMessages(to).stream().forEach(e -> {
                TelegramBotUtils.sendMessage(tg_bot, e, chat_id);
            });
            
            SessionManager.getInstance().getSession(chat_id).setState(State.IDLE);
        }
    }
    
    private static List<String> generateMessages(List <String> to) {
        var iterator = to.iterator();
        int counter = 0;
        var intermediate = new StringBuffer();
        List <String> messages = new ArrayList<>();
        
        while(iterator.hasNext()) {
            intermediate.append(iterator.next());
            counter++;
            if(counter == 10) {
                
                messages.add(intermediate.toString());
                
                counter = 0;
                intermediate.delete(0, intermediate.length());
            }
        }
        return messages;
    }
    
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
