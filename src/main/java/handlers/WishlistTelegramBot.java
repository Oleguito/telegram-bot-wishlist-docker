package handlers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class WishlistTelegramBot extends TelegramLongPollingBot {
    
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            var message = update.getMessage();
            if(message.hasText()) {
                sendMessage(message.getText(), message.getChatId().toString());
            }
        }
    }
    
    private void sendMessage(String text, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
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
