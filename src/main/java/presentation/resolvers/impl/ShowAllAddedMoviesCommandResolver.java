package presentation.resolvers.impl;

import application.service.MoviesService;
import application.service.impl.MoviesServiceImpl;
import application.service.sessions.SessionManager;
import application.service.statemachine.State;
import domain.model.entity.MovieEntity;
import infrastructure.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import presentation.resolvers.CommandResolver;

import java.util.List;

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
    public void resolveCommand(TelegramLongPollingBot tgBot, String text, Long chatId) {
        if (text.startsWith("/showall")) {

            List<MovieEntity> movies = moviesService.getMovies(chatId);

            if (movies.isEmpty()) {
                TelegramBotUtils.sendMessage(tgBot, "У вас нет добавленных фильмов:(", chatId);
            } else {
                movies.stream()
                        .forEach(e -> {
                            TelegramBotUtils.sendMessage(tgBot, e.toString(), chatId); // TODO: поправить вывод информации о фильме
                        });
            }
            sessionManager.getSession(chatId).setState(State.IDLE);
        }
    }
}
