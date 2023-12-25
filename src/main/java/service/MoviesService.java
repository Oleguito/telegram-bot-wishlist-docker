package service;

import model.entity.MovieEntity;

public interface MoviesService {
    void saveMovie(MovieEntity movieEntity, Long chatID);
}
