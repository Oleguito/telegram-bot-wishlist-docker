package repository;

import model.entity.UserEntity;

import java.util.Optional;

public interface UserRepo {

    public void saveUser(UserEntity userEntity);
    
    public Optional <String> getUsername(long user_id);
}