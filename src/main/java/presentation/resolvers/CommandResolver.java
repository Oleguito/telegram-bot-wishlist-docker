package presentation.resolvers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public interface CommandResolver {

    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id);

    public String getCommandName();


}
