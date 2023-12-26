package resolvers.impl;

import lombok.Getter;
import model.db.DBManager;
import model.entity.MovieEntity;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parsers.ParserRepo;
import parsers.impl.ParserRepoImpl;
import repository.MoviesRepo;
import resolvers.CommandResolver;
import service.MoviesService;
import service.impl.MoviesServiceImpl;
import service.sessions.Session;
import service.sessions.SessionManager;
import service.statemachine.State;

import utils.TelegramBotUtils;

import java.util.List;

import utils.TGUtils;


public class AddMovieCommandResolver implements CommandResolver {

    private final ParserRepo parserRepo;
    private final MoviesService moviesService;
    private final String COMMAND_NAME = "/add";

    public AddMovieCommandResolver() {
        this.parserRepo = new ParserRepoImpl();

        this.moviesService = new MoviesServiceImpl();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;

    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {

        if (text.startsWith("/add")) {
            try {
                TGUtils.sendMessage(tg_bot, text, chat_id);
                setState(chat_id, State.ADD);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else {
            MovieEntity movie = parserRepo.parse(text);
            moviesService.saveMovie(movie, chat_id);
            setState(chat_id, State.IDLE);
        }

    }

}
