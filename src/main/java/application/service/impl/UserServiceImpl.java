package application.service.impl;

import domain.model.db.DBManager;
import domain.model.entity.UserEntity;
import infrastructure.repository.UserRepo;
import application.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private static UserService instance;

    public UserServiceImpl() {
        this.userRepo = DBManager.getInstance().getUserRepo();
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        userRepo.saveUser(userEntity);
    }

    @Override
    public UserEntity getUser(Long chatID) {
        return userRepo.getUser(chatID);
    }

    @Override
    public Optional<String> getUsername(long user_id) {
        return userRepo.getUsername(user_id);
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }


}
