package handlers;

import buttons.ButtonGenerator;
import commands.Commands;
import config.Configuration;
import model.db.DBManager;
import model.entity.HistoryEntity;
import model.entity.UserEntity;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import resolvers.CommandResolver;
import service.sessions.Session;
import service.sessions.SessionManager;
import service.statemachine.State;
import utils.TelegramBotUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WishlistTelegramBot extends TelegramLongPollingBot {

    Map<String, CommandResolver> resolvers = Configuration.resolvers;
    private final SessionManager sessionManager = SessionManager.getInstance();

    private static String getResolverName(Long chatID) {
        Session session = SessionManager.getInstance().getSession(chatID);
        String resolverName = session.getState().getValue();
        return resolverName;
    }

    private void setSessionStateForThisUser(Long chatID, State state) {
        sessionManager.getSession(chatID).setState(state);
    }

    private State getUserState(Long chatID) {
        return sessionManager.getSession(chatID).getState();
    }

    private void createSessionForThisUser(Long chatID) {
        sessionManager.createSession(chatID);
    }

    private static void addUserToDatabaseIfHesNotThere(Long chatID, String username) {
        DBManager.getInstance().getUserRepo().saveUser(UserEntity.builder()
                .id(chatID)
                .username(username)
                .build());

    }

    private static void addCommandToHistoryDB(Long chatID, String callData) {
        DBManager.getInstance().getHistoryRepo().insert(HistoryEntity.builder()
                .user_id(chatID)
                .command(callData)
                .operation_time(Timestamp.from(Instant.now()))
                .build());
    }

    public void init() throws TelegramApiException {
        this.execute(ButtonGenerator.generateMenuButtons());
    }

    @Override
    public void onUpdateReceived(Update update) {

        /* Обработка кнопок */
        if (update.hasCallbackQuery()) {
            var query = update.getCallbackQuery();

            String callData = query.getData();
            Long chatID = query.getMessage().getChatId();

            String username = update.getCallbackQuery().getFrom().getUserName();
            addUserToDatabaseIfHesNotThere(chatID, username); // TODO: вынести в прокси

            createSessionForThisUser(chatID);

            addCommandToHistoryDB(chatID, callData); // TODO: вынести в прокси

            processCommand(callData, chatID, callData);
            if (getUserState(chatID) == State.IDLE) {
                greetingScreen(chatID);
            }
        }

        /* Обработка сообщений пользователя */
        if (update.hasMessage()) {

            var message = update.getMessage();

            if (message.hasText()) {
                var text = message.getText();
                var chatID = message.getChatId();

                String username = message.getChat().getUserName();
                addUserToDatabaseIfHesNotThere(chatID, username); // TODO: вынести в прокси

                createSessionForThisUser(chatID);

                addCommandToHistoryDB(chatID, text); // TODO: вынести в прокси

                if (text.startsWith("/start")) {
                    setSessionStateForThisUser(chatID, State.IDLE);
                    greetingScreen(chatID);
                } else {
                    String resolverName = getResolverName(chatID);
                    processCommand(text, chatID, resolverName);
                    if (SessionManager.getInstance().getSession(chatID).getState() == State.IDLE) {
                        greetingScreen(chatID);
                    }
                }
            }
        }

    }

    private void processCommand(String text, Long chatID, String resolverName) {
        CommandResolver commandResolver = resolvers.get(resolverName);
        commandResolver.resolveCommand(this, text, chatID);
    }

    private void greetingScreen(Long chat_id) {

        TelegramBotUtils.sendImage(this, "src/main/resources/logo.jpg", chat_id);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat_id);
        sendMessage.setText("Сделайте выбор 😉");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonLines = new ArrayList<>();
        for (var i : Commands.values()) {
            buttonLines.add(List.of(ButtonGenerator.generateInlineButton(i.getValue())));
        }

        markupInline.setKeyboard(buttonLines);
        sendMessage.setReplyMarkup(markupInline);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Что-то пошло не так при отправке сообщения");
        }
    }

    @Override
    public String getBotUsername() {
        return "kinopoiskwishlistbot";
    }

    @Override
    public String getBotToken() {
        return "6305522534:AAGkX6E291mT16sCeCx3pGyLFSuJWaDI-Bo";
    }
}
