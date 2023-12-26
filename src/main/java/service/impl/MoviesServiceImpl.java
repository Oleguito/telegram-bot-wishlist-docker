package service.impl;

import model.db.DBManager;
import model.entity.MovieEntity;
import repository.MoviesRepo;
import service.MoviesService;

public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepo moviesRepo;

    public MoviesServiceImpl() {
        this.moviesRepo = DBManager.getInstance().getMoviesRepo();
    }

    @Override

    public void saveMovie(MovieEntity movieEntity, Long chat_id) {
        moviesRepo.saveMovie(movieEntity, chat_id);

    }
}
