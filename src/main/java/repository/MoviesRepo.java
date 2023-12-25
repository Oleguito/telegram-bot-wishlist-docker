package repository;

import model.entity.MovieEntity;

import java.util.List;

public interface MoviesRepo {

    public void saveMovie(MovieEntity movieEntity);
    
    List <MovieEntity> getMovies(Long chatID);
}
