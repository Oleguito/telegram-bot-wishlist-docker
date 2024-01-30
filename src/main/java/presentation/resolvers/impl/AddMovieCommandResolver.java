package presentation.resolvers.impl;

import domain.model.entity.MovieEntity;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import infrastructure.utils.parsers.Parser;
import infrastructure.utils.parsers.impl.KinopoiskParser;
import presentation.resolvers.CommandResolver;
import application.service.MoviesService;
import application.service.impl.MoviesServiceImpl;
import application.service.sessions.SessionManager;
import application.service.statemachine.State;

import infrastructure.utils.TelegramBotUtils;


public class AddMovieCommandResolver implements CommandResolver {

    private final Parser parser;
    private final MoviesService moviesService;
    private final String COMMAND_NAME = "/add";

    public AddMovieCommandResolver() {
        this.parser = new KinopoiskParser();
        this.moviesService = new MoviesServiceImpl();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {

        if (text.startsWith("/add")) {
            TelegramBotUtils.sendMessage(tg_bot, """
                    Введите ссылку с кинопоиска
                    """, chat_id);
            setState(chat_id, State.ADD);
        } else {
            
            if(!text.matches("https:\\/\\/www.kinopoisk.ru\\/[a-zA-Z+]+\\/\\d+[\\/]*")) {
                TelegramBotUtils.sendMessage(tg_bot, """
                        Не похоже на ссылку с кинопоиска :)
                        """, chat_id);
                TelegramBotUtils.sendMessage(tg_bot, """
                        Может, ввести что-нибудь более читабельное? 😉
                        """, chat_id);
                return;
            }
            
            MovieEntity movie = parser.parse(text);
            
            //TODO: это реализовано в процедуре save_film
            if(movieAlreadyRegisteredInUsersMoviesDB(movie, chat_id)) {
                TelegramBotUtils.sendMessage(tg_bot, """
                    Такое кино вы уже добавляли!)
                    """, chat_id);
                setState(chat_id, State.IDLE);
                return;
            }
            
            moviesService.saveMovie(movie, chat_id);
            TelegramBotUtils.sendMessage(tg_bot, """
                    Поздравляем! Фильм успешно добавлен в базу данных!!!!
                    """, chat_id);
            setState(chat_id, State.IDLE);
        }

    }
    
    private boolean movieAlreadyRegisteredInUsersMoviesDB(MovieEntity movie, Long chat_id) {
        return moviesService.movieRegisteredInUsersMovies(movie, chat_id);
    }
    
    private void setState(Long chatId, State state) {
        SessionManager.getInstance().getSession(chatId).setState(state);
    }
    
}
