package presentation.commands;

public enum Commands {
    
    START(Command.builder()
            .commandText("/add")
            .commandCallbackData("/add")
            .commandButtonText("Добавить фильм по ссылке")
            .build()),
    FIND_BY_TITLE(Command.builder()
            .commandText("/findbytitle")
            .commandCallbackData("/findbytitle")
            .commandButtonText("Найти фильм по названию")
            .build()),
    DELETE(Command.builder()
            .commandText("/delete")
            .commandCallbackData("/delete")
            .commandButtonText("Очистить список моих фильмов")
            .build()),
    HISTORY(Command.builder()
            .commandText("/history")
            .commandCallbackData("/history")
            .commandButtonText("История моих сообщений боту")
            .build()),
    SHOW_ALL(Command.builder()
            .commandText("/showall")
            .commandCallbackData("/showall")
            .commandButtonText("Показать все добавленные фильмы")
            .build());
    
    Command value;
    
    Commands(Command built) {
        this.value = built;
    }
    
    public Command getValue() {
        return value;
    }
}
