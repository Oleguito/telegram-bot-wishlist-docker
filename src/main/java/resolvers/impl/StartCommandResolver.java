package resolvers.impl;

import buttons.ButtonGenerator;
import commands.Commands;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import resolvers.CommandResolver;
import service.sessions.Session;
import service.sessions.SessionManager;
import service.statemachine.State;
import utils.TelegramBotUtils;

import java.util.ArrayList;
import java.util.List;

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
