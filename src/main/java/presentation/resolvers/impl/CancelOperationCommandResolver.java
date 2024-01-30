package presentation.resolvers.impl;

import application.service.MoviesService;
import application.service.impl.MoviesServiceImpl;
import application.service.sessions.SessionManager;
import application.service.statemachine.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import presentation.resolvers.CommandResolver;

public class CancelOperationCommandResolver implements CommandResolver {
    
    private final String COMMAND_NAME = "/cancel";
    private final MoviesService moviesService;
    private final SessionManager sessionManager = SessionManager.getInstance();
    
    public CancelOperationCommandResolver() {
        moviesService = new MoviesServiceImpl();
    }
    
    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        sessionManager.getSession(chatId).setState(State.IDLE);
    }
    
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
