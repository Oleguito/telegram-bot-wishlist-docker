package repository.impl;

import model.entity.MovieEntity;
import repository.MoviesRepo;

import java.sql.Connection;

public class MoviesRepoImpl implements MoviesRepo {
    
    private final Connection connection;
    
    public MoviesRepoImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void saveMovie(MovieEntity movieEntity) {
    
    }
}
