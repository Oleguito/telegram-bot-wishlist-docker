package infrastructure.repository;

import domain.model.entity.MovieEntity;

public interface UsersMoviesRepo {
    boolean movieRegistered(MovieEntity movie, Long chatID);
    
    void deleteMoviesOfUser(Long chatId);
}
