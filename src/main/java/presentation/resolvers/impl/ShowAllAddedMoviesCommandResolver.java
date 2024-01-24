package presentation.resolvers.impl;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import presentation.resolvers.CommandResolver;
import application.service.MoviesService;
import application.service.impl.MoviesServiceImpl;
import application.service.sessions.SessionManager;
import application.service.statemachine.State;
import infrastructure.utils.TelegramBotUtils;

public class ShowAllAddedMoviesCommandResolver implements CommandResolver {

    private final String COMMAND_NAME = "/showall";
    private final MoviesService moviesService;
    private final SessionManager sessionManager = SessionManager.getInstance();

    public ShowAllAddedMoviesCommandResolver() {
        this.moviesService = new MoviesServiceImpl();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        if(text.startsWith("/showall")) {

            moviesService.getMovies(chat_id)
                    .stream()
                    .forEach(e -> {
                        TelegramBotUtils.sendMessage(tg_bot,e.toString(),chat_id); // TODO: поправить вывод информации о фильме
                    });
            sessionManager.getSession(chat_id).setState(State.IDLE);
        }
    }
}
