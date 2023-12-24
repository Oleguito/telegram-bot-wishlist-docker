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
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Веедите ссылку на фильм с кинопоиска");
            sendMessage.setChatId(chat_id);

            try {
                tg_bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else {

            MovieEntity movie = parserRepo.parse(text);
            moviesRepo.saveMovie(movie);

        }

    }
}
