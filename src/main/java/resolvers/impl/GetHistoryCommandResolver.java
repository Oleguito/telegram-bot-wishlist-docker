package resolvers.impl;

import org.glassfish.grizzly.http.util.TimeStamp;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import resolvers.CommandResolver;
import service.HistoryService;
import service.impl.HistoryServiceImpl;
import service.sessions.SessionManager;
import service.statemachine.State;
import utils.TelegramBotUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetHistoryCommandResolver implements CommandResolver {

    private final HistoryService historyService;
    private final String COMMAND_NAME = "/history";
    private final SessionManager sessionManager = SessionManager.getInstance();

    public GetHistoryCommandResolver() {
        this.historyService = new HistoryServiceImpl();
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        if(text.startsWith("/history")) {
            var to = historyService.getUserHistory(chat_id)
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
