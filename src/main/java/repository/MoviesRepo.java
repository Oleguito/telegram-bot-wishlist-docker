package repository;

import model.entity.MovieEntity;

public interface MoviesRepo {

    public void saveMovie(MovieEntity movieEntity, Long chat_id);

}
