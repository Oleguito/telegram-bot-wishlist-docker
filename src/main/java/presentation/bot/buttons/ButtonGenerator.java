package presentation.bot.buttons;

import presentation.commands.Command;
import presentation.commands.StartCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ButtonGenerator {
    public static InlineKeyboardButton generateInlineButton(Command type) {
        InlineKeyboardButton newInlineButton = new InlineKeyboardButton();

        newInlineButton.setText(type.getCommandButtonText());
        newInlineButton.setCallbackData(type.getCommandCallbackData());

        return newInlineButton;
    }
    
    public static SetMyCommands generateMenuButtons () {
        return new SetMyCommands(
                StartCommands.init(),
                new BotCommandScopeDefault(),
                null);
    }
}
