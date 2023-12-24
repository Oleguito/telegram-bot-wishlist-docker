package service;

import model.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    public void saveUser(UserEntity userEntity);
    public Optional<String> getUsername(long user_id);
}
