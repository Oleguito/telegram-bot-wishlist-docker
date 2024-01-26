package presentation.resolvers.impl;

import application.service.MoviesService;
import application.service.impl.MoviesServiceImpl;
import application.service.sessions.SessionManager;
import application.service.statemachine.State;
import infrastructure.utils.TelegramBotUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import presentation.resolvers.CommandResolver;

import java.util.stream.Collectors;

public class FindMovieByTitleCommandResolver implements CommandResolver {

    private final String COMMAND_NAME = "/findbytitle";
    private final MoviesService moviesService;

    public FindMovieByTitleCommandResolver() {
        this.moviesService = new MoviesServiceImpl();
    }

    private static void setSessionStateForThisUser(Long chatId, State state) {
        SessionManager.getInstance().getSession(chatId).setState(state);
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chatId) {
        if (text.startsWith("/findbytitle")) {

            TelegramBotUtils.sendMessage(tg_bot, "Введите название фильма", chatId);
            setSessionStateForThisUser(chatId, State.FIND_BY_TITLE);

        } else {
            var movies = moviesService.getMovies(chatId).stream()
                    .filter(
                            e -> e.getTitle().toLowerCase().contains(text.toLowerCase())
                    )
                    .toList();
            if (movies.isEmpty()) {
                TelegramBotUtils.sendMessage(tg_bot, """
                        Ничего не нашлось... 😞 Может, ачипятка??)
                        """, chatId);
                setSessionStateForThisUser(chatId, State.IDLE);
                return;
            }
            var to = movies.stream()
                    .map(e -> e.toString())
                    .collect(Collectors.joining("\n"));
            TelegramBotUtils.sendMessage(tg_bot, to, chatId);
            setSessionStateForThisUser(chatId, State.IDLE);
        }
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
