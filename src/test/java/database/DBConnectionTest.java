package database;

import domain.model.db.DBConnection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DBConnectionTest {
    
    
    @Test
    @DisplayName("Database connection instance is not null")
    void database_connection_instance_is_not_null() {
        Assertions.assertThat(DBConnection.getInstance()).isNotNull();
    }
    
    @Test
    @DisplayName("Database connection is not null")
    void database_connection_is_not_null() {
        assertThat(DBConnection.getInstance().getConnection()).isNotNull();
    }

}