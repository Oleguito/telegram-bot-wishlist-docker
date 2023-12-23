package repository.impl;

import model.entity.UserEntity;
import repository.UserRepo;

import java.sql.Connection;

public class UserRepoImpl implements UserRepo {
    
    private final Connection connection;
    
    public UserRepoImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void saveUser(UserEntity userEntity) {

    }

    @Override
    public void getUsername(int user_id) {

    }
}
