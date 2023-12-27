package utils;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class TelegramBotUtils {
    public static void sendMessage(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chat_id);
        try {
            tg_bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Не удалось выпендриться", e);
        }
    }
    
    public static void sendImage(TelegramLongPollingBot tgBot, String filePath, Long chatId) {
        
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(new InputFile(new File(filePath)));
        try {
            tgBot.execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            throw new RuntimeException("ГОРИМ!!!", e);
        }
        
    }
    
    
}
