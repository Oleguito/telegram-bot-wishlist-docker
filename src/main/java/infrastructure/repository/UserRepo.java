package infrastructure.repository;

import domain.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepo {

    public void saveUser(UserEntity userEntity);
    public UserEntity getUser(Long chatID);
    public Optional <String> getUsername(long user_id);
}