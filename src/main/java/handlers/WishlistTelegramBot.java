package handlers;

import buttons.ButtonGenerator;
import commands.Commands;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import resolvers.CommandResolver;
import resolvers.impl.AddMovieCommandResolver;
import service.sessions.SessionManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistTelegramBot extends TelegramLongPollingBot {

    Map<String, CommandResolver> resolvers;

    public WishlistTelegramBot() {
        super();
        this.resolvers = new HashMap<>();
        this.resolvers.put("/add", new AddMovieCommandResolver());
    }

    @Override
    public void onUpdateReceived(Update update) {
        
        /* Обработка кнопок */
        if(update.hasCallbackQuery()) {
            var query = update.getCallbackQuery();
            
            String callData = query.getData();
            Long chatID = query.getMessage().getChatId();
            
            // sendMessage(call_data, chat_id.toString());
            // sendMessage(chat_id.toString(), chat_id.toString());

            CommandResolver commandResolver = resolvers.get(callData);
            commandResolver.resolveCommand(this, callData, chatID);

            SessionManager.getInstance().manageSession(callData, chatID);
            
        }
        
        /* Обработка сообщений пользователя */
        if(update.hasMessage()) {

            var message = update.getMessage();
            
            if(message.hasText()) {
                var commandText = message.getText();
                var chatID = message.getChatId();
                
                if(commandText.startsWith("/start")) {
                    greetingScreen(message);
                } else {

                }

            }
        }
    }
    
    private void greetingScreen(Message message) {
        // sendMessage(message.getText(), message.getChatId().toString());
        
        sendImageUploadingAFile("src/main/resources/shredder.jpg", message.getChatId().toString());
        
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Сделайте выбор");
        
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List <InlineKeyboardButton>> buttonLines = new ArrayList <>();
        for(var i : Commands.values()) {
            buttonLines.add(List.of(ButtonGenerator.generateButton(i.getValue())));
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
            e.printStackTrace();
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
