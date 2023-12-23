package handlers;

import buttons.ButtonGenerator;
import commands.Commands;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WishlistTelegramBot extends TelegramLongPollingBot {
    
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            var message = update.getMessage();
            if(message.hasText()) {
                // sendMessage(message.getText(), message.getChatId().toString());
                
                sendImageUploadingAFile("src/main/resources/shredder.jpg", message.getChatId().toString());
                
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("ОлегОлегОлегОлегОлегОлегОлегОлегОлегОлег");
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
        }
    }
    
    private void sendMessage(String text, String chatId) {
    
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
