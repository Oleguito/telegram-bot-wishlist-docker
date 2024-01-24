package application.service;

import domain.model.entity.MovieEntity;

import java.util.List;

public interface MoviesService {
    public void saveMovie(MovieEntity movieEntity, Long chat_id);
    
    boolean movieRegisteredInUsersMovies(MovieEntity movie, Long chatId);
    
    void deleteAllMoviesOfUserFromDatabase(Long chatId);

    List<MovieEntity> getMovies(Long chat_id);

}
