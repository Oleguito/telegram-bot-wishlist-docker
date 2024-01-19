package database;

import model.db.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

public class QueringDatabaseTests {
    
    private static Connection connection;
    
    @BeforeAll
    static void beforeAll() {
        connection = DBConnection.getInstance().getConnection();
    }
    
    // @Test
    // @DisplayName("Select all users")
    // void select_all_users() throws Exception {
    //     String query = "select * from users where id = ? limit 1";
    //     PreparedStatement preparedStatement = SQLUtils.getPreparedStatement(query, connection);
    //     preparedStatement.setLong(1, 1337L);
    //     var resultSet = preparedStatement.executeQuery();
    //
    //     resultSet.next();
    //     Optional<String> username = Optional.ofNullable(resultSet.getString("username"));
    //
    //     resultSet.close();
    //     preparedStatement.close();
    // }
    

}
