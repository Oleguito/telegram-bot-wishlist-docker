package repository;

import model.entity.MovieEntity;


import java.util.List;

public interface MoviesRepo {

    public void saveMovie(MovieEntity movieEntity, Long chat_id);
    public List<MovieEntity> getMovies(Long chatID);

}
