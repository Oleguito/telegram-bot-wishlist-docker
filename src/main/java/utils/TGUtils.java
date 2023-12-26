package utils;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TGUtils {

    public static void sendMessage(TelegramLongPollingBot bot, String text, Long chat_id) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chat_id);
        bot.execute(sendMessage);
    }

}
