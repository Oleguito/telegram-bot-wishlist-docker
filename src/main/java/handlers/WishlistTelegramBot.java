package handlers;

import buttons.ButtonGenerator;
import commands.Commands;
import commands.StartCommands;
import model.db.DBManager;
import model.entity.HistoryEntity;
import model.entity.UserEntity;

import config.Configuration;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import resolvers.CommandResolver;
import resolvers.impl.AddMovieCommandResolver;
import resolvers.impl.FindMovieByTitleCommandResolver;
import resolvers.impl.GetHistoryCommandResolver;
import resolvers.impl.ShowAllAddedMoviesCommandResolver;
import service.sessions.Session;
import service.sessions.SessionManager;
import service.statemachine.State;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistTelegramBot extends TelegramLongPollingBot {

    Map<String, CommandResolver> resolvers = Configuration.resolvers;

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

            String username = getUserNameFromCallBackQuery(update);
            addUserToDatabaseIfHesNotThere(chatID, username);
            
            createSessionForThisUser(chatID);
            
            addCommandToHistoryDB(chatID, callData);
            
            processCommand(callData, chatID, callData);
            if(getUserState(chatID) == State.IDLE) {
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
                addUserToDatabaseIfHesNotThere(chatID, username);
                
                createSessionForThisUser(chatID);
                
                addCommandToHistoryDB(chatID, text);
                
                if (text.startsWith("/start")) {
                    setSessionStateForThisUser(chatID, State.IDLE);
                    greetingScreen(chatID);
                } else {
                    String resolverName = getResolverName(chatID);
                    processCommand(text, chatID, resolverName);
                    if(SessionManager.getInstance().getSession(chatID).getState() == State.IDLE) {
                        greetingScreen(chatID);
                    }
                }
            }
        }

    }
    
    private static String getResolverName(Long chatID) {
        Session session = SessionManager.getInstance().getSession(chatID);
        String resolverName = session.getState().getValue();
        return resolverName;
    }
    
    private static void setSessionStateForThisUser(Long chatID, State state) {
        SessionManager.getInstance().getSession(chatID).setState(state);
    }
    
    private static State getUserState(Long chatID) {
        return SessionManager.getInstance().getSession(chatID).getState();
    }
    
    private static void createSessionForThisUser(Long chatID) {
        SessionManager.getInstance().createSession(chatID);
    }
    
    private static String getUserNameFromCallBackQuery(Update update) {
        return update.getCallbackQuery().getFrom().getUserName();
    }
    
    private static void addUserToDatabaseIfHesNotThere(Long chatID, String username) {
        if(userID_NotFound_InDB(chatID)) {
            DBManager.getInstance().getUserRepo().saveUser(UserEntity.builder()
                            .id(chatID)
                            .username(username)
                    .build());
        }
        
    }
    
    private static boolean userID_NotFound_InDB(Long chatID) {
        return DBManager.getInstance().getUserRepo().getUsername(chatID).isEmpty();
    }
    
    private static void addCommandToHistoryDB(Long chatID, String callData) {
        DBManager.getInstance().getHistoryRepo().insert(HistoryEntity.builder()
                .user_id(chatID)
                .command(callData)
                .operation_time(Timestamp.from(Instant.now()))
                .build());
    }
    
    private void processCommand(String text, Long chatID, String resolverName) {
        CommandResolver commandResolver = resolvers.get(resolverName);
        commandResolver.resolveCommand(this, text, chatID);
    }

    private void greetingScreen(Long chat_id) {
        // sendMessage(message.getText(), message.getChatId().toString());

        sendImageUploadingAFile("src/main/resources/shredder.jpg", chat_id.toString());

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

    private void sendMessage(String text, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось отправить текстовое сообщение пользователю через Telegram Bots API");
        }
    }

    public void sendImageUploadingAFile(String filePath, String chatId) {
        // Create send method
        SendPhoto sendPhotoRequest = new SendPhoto();
        // Set destination chat id
        sendPhotoRequest.setChatId(chatId);
        // Set the photo file as a new photo (You can also use InputStream with a constructor overload)
        sendPhotoRequest.setPhoto(new InputFile(new File(filePath)));
        try {
            // Execute the method
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            throw new RuntimeException("ГОРИМ!!!", e);
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
