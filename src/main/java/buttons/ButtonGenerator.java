package buttons;

import commands.Command;
import commands.Commands;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ButtonGenerator {
    public static InlineKeyboardButton generateButton(Command type) {
        InlineKeyboardButton newInlineButton = new InlineKeyboardButton();
        newInlineButton.setText(type.getCommandButtonText());
        newInlineButton.setCallbackData(type.getCommandCallbackData());
        return newInlineButton;
    }
}
