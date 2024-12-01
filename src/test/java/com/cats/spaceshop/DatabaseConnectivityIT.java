package com.cats.spaceshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DatabaseConnectivityIT extends AbstractIt {

    @Test
    void testDatabaseConnectivity() throws SQLException {
        String jdbcUrl = String.format(
                "jdbc:postgresql://%s:%d/%s",
                POSTGRES_CONTAINER.getHost(),
                POSTGRES_CONTAINER.getMappedPort(POSTGRES_PORT),
                "spaceMarketTest"
        );

        try (Connection connection = DriverManager.getConnection(
                jdbcUrl,
                "postgres",
                "mydb111")) {
            assertNotNull(connection);
            assertTrue(connection.isValid(2));
        }
    }
}
