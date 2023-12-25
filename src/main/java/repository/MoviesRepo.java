package repository;

import model.entity.MovieEntity;

import java.util.List;

public interface MoviesRepo {
    
    void saveMovie(MovieEntity movieEntity, Long chatID);
    
    List <MovieEntity> getMovies(Long chatID);
}
