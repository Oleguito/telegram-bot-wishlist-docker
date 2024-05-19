// package services;

// import domain.model.db.DBConnection;
// import domain.model.entity.UserEntity;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import application.service.UserService;
// import application.service.impl.UserServiceImpl;
// import infrastructure.utils.SQLUtils;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// import static org.assertj.core.api.Assertions.*;
// import static org.junit.jupiter.api.Assertions.*;

// public class UserServiceTest {
    
//     @Test
//     @DisplayName("Can get UserService instance")
//     void can_get_user_service_instance() {
//         UserService us = UserServiceImpl.getInstance();
//         assertThat(us).isNotNull();
//     }
    
//     @Test
//     @DisplayName("Can save user to data base")
//     void can_save_user_to_data_base() throws SQLException {
//         UserService us = UserServiceImpl.getInstance();
//         UserEntity user = getVasyaPupkin();
//         us.saveUser(user);
//         assertTrue(userCanBeFoundInDB());
//     }
    
//     private static UserEntity getVasyaPupkin() {
//         return UserEntity.builder()
//                 .id(1337)
//                 .username("vasya_pupkin")
//                 .build();
//     }
    
//     @Test
//     @DisplayName("Can get username")
//     void can_get_username() {
//         UserService us = UserServiceImpl.getInstance();
//         UserEntity vasyaPupkin = getVasyaPupkin();

//         us.saveUser(vasyaPupkin);

//         String usernameFromDB = us.getUsername(vasyaPupkin.getId()).get();
//         String vasyaPupkinUsername = vasyaPupkin.getUsername();
        
//         assertTrue(usernameFromDB.equals(vasyaPupkinUsername));
//     }
    
    
//     private boolean userCanBeFoundInDB() throws SQLException {
//         String query = "select * from users where id = ? limit 1";
//         Connection connection = DBConnection.getInstance().getConnection();
//         PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
//         preparedStatement.setLong(1, 1337L);
//         var resultSet = preparedStatement.executeQuery();

//         resultSet.next();
        
//         boolean result = 1337L == resultSet.getLong(1) &&
//                 "vasya_pupkin".equals(resultSet.getString(2));
        
//         resultSet.close();
//         preparedStatement.close();
        
//         return result;
//     }
    
    
// }






