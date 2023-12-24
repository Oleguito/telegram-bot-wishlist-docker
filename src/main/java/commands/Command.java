package commands;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Command {
    private final String commandText;
    private final String commandButtonText;
    private final String commandCallbackData;
}
