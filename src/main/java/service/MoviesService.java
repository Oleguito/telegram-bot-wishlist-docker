package service;

import model.entity.MovieEntity;

public interface MoviesService {
    public void saveMovie(MovieEntity movieEntity, Long chat_id);
    
    boolean movieRegisteredInUsersMovies(MovieEntity movie, Long chatId);
}
