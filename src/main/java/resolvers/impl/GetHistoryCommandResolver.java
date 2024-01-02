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
        if (text.startsWith("/history")) {
            var to = historyService.getUserHistory(chat_id);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ваша история команд: \n\n");
            to.forEach(element -> stringBuilder.append(String.format("Команда: %s, время выполнения: %s\n", element.getCommand(), element.getOperation_time().toLocalDateTime().toString())));

            TelegramBotUtils.sendMessage(tg_bot, stringBuilder.toString(), chat_id);

            sessionManager.getSession(chat_id).setState(State.IDLE);
        }

    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
