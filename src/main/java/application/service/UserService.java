package application.service;

import domain.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    public void saveUser(UserEntity userEntity);
    public Optional<String> getUsername(long user_id);
    public UserEntity getUser(Long chatID);
}
