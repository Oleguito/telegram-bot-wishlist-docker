package database;

import domain.model.db.DBConnection;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;

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
