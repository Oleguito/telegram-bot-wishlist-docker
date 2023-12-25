package resolvers.impl;

import model.db.DBManager;
import model.entity.MovieEntity;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import parsers.ParserRepo;
import parsers.impl.ParserRepoImpl;
import repository.MoviesRepo;
import resolvers.CommandResolver;
import service.sessions.Session;
import service.sessions.SessionManager;
import service.statemachine.State;

import java.util.List;

public class AddMovieCommandResolver implements CommandResolver {

    private final ParserRepo parserRepo;
    private final MoviesRepo moviesRepo;

    public AddMovieCommandResolver() {
        this.parserRepo = new ParserRepoImpl();
        this.moviesRepo = DBManager.getInstance().getMoviesRepo();
        
    }

    @Override
    public void resolveCommand(TelegramLongPollingBot tg_bot, String text, Long chat_id) {

        if (text.startsWith("/add")) {
            sendMessage(tg_bot, "Введите ссылку на фильм с кинопоиска", chat_id);
            
            setState(chat_id, State.ADD);

        } else {
            
            MovieEntity movie = parserRepo.parse(text);
            List <MovieEntity> movies = moviesRepo.getMovies(chat_id);
            
            if(contains(movies, movie)) {
                sendMessage(tg_bot, "Этот фильм уже есть в базе данных! :)", chat_id);
                return;
            }
            
            moviesRepo.saveMovie(movie);
            setState(chat_id, State.IDLE);
            
            sendMessage(tg_bot, "Фильм успешно добавлен в базу данных! :)", chat_id);
        }

    }
    
    private boolean contains(List <MovieEntity> movies, MovieEntity movie) {
        for(var m : movies) {
            if(m.equals(movie)) return true;
        }
        return false;
    }
    
    private static void sendMessage(TelegramLongPollingBot tg_bot, String text, Long chat_id) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chat_id);
        
        try {
            tg_bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Не удалось выпендриться");
        }
    }
    
    private void setState(Long chat_id, State state) {
        Session session = SessionManager.getInstance().getSession(chat_id);
        session.setState(state);
    }
}
