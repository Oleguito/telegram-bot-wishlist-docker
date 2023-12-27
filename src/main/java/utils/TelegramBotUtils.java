package utils;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
}
