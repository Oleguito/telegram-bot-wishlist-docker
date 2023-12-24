package commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotCommands {

    START("/start", "запуск бота");
//    GET("/get", "Получить список объявлений"),
//    HISTORY("/history", "Получить историю сообщений");

    private final String command;
    private final String description;

}
