package repository;

import model.entity.MovieEntity;
import service.MoviesService;

public interface UsersMoviesRepo {
    boolean movieRegistered(MovieEntity movie, Long chatID);
    
    void deleteMoviesOfUser(Long chatId);
}
