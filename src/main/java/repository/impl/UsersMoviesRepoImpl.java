package repository.impl;

import model.entity.MovieEntity;
import repository.UsersMoviesRepo;

public class UsersMoviesRepoImpl implements UsersMoviesRepo {
    @Override
    public boolean movieRegistered(MovieEntity movie, Long chatID) {
        return false;
    }
}
