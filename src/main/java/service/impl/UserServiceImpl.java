package service.impl;

import model.db.DBManager;
import model.entity.UserEntity;
import repository.UserRepo;
import service.UserService;

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
