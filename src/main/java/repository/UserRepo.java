package repository;

import model.entity.UserEntity;

public interface UserRepo {

    public void saveUser(UserEntity userEntity);
    public void getUsername(int user_id);

}
