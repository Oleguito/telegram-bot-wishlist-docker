package presentation.resolvers.impl;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import presentation.resolvers.CommandResolver;
import application.service.sessions.SessionManager;
import application.service.statemachine.State;

public class StartCommandResolver implements CommandResolver {

    private final SessionManager sessionManager;

    public StartCommandResolver() {
        this.sessionManager = SessionManager.getInstance();
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tgBot, String text, Long chatID) {
        sessionManager.getSession(chatID).setState(State.IDLE);
    }

    @Override
    public String getCommandName() {
        return State.START.getValue();
    }


}
