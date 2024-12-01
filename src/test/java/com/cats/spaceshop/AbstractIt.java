package com.cats.spaceshop;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@DirtiesContext
//public abstract class AbstractIt {
//
//    protected static final int POSTGRES_PORT = 5432;
//
//    static final GenericContainer POSTGRES_CONTAINER = new GenericContainer("postgres:15.6-alpine")
//            .withEnv("POSTGRES_PASSWORD", "mydb111")
//            .withEnv("POSTGRES_DB", "spaceMarketTest")
//            .withExposedPorts(POSTGRES_PORT);
//
//    @RegisterExtension
//    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
//            .options(wireMockConfig().dynamicPort())
//            .configureStaticDsl(true)
//            .build();
//
//    @DynamicPropertySource
//    static void setupTestContainerProperties(DynamicPropertyRegistry registry) {
//        registry.add("application.payment-service.base-path", wireMockServer::baseUrl);
//        registry.add("spring.datasource.url", () -> format("jdbc:postgresql://%s:%d/spaceMarketTest",
//                POSTGRES_CONTAINER.getHost(), POSTGRES_CONTAINER.getMappedPort(POSTGRES_PORT)));
//        registry.add("spring.datasource.username", () -> "postgres");
//        registry.add("spring.datasource.password", () -> "mydb111");
//
//        WireMock.configureFor(wireMockServer.getPort());
//    }
//
//    @BeforeAll
//    static void setUp() {
//        // Starting the PostgreSQL container
//        POSTGRES_CONTAINER.start();
//    }
//
//    @AfterAll
//    static void tearDown() {
//        // Stopping the PostgreSQL container after tests
//        POSTGRES_CONTAINER.stop();
//    }
//
//    // Your test methods would go here
//
//}
//


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public abstract class AbstractIt {

    protected static final int POSTGRES_PORT = 5432;

    static final GenericContainer POSTGRES_CONTAINER = new GenericContainer("postgres:15.6-alpine")
            .withEnv("POSTGRES_PASSWORD", "mydb111")
            .withEnv("POSTGRES_DB", "spaceMarketTest")
            .withExposedPorts(POSTGRES_PORT);

    static {
        POSTGRES_CONTAINER.start();
        System.out.println("Postgres container started at: " + POSTGRES_CONTAINER.getHost() + ":" + POSTGRES_CONTAINER.getMappedPort(POSTGRES_PORT));
    }

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .configureStaticDsl(true)
            .build();

    @DynamicPropertySource
    static void setupTestContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> format("jdbc:postgresql://%s:%d/spaceMarketTest",
                POSTGRES_CONTAINER.getHost(), POSTGRES_CONTAINER.getMappedPort(POSTGRES_PORT)));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "mydb111");

        WireMock.configureFor(wireMockServer.getPort());
    }

    @AfterAll
    static void tearDown() {
        POSTGRES_CONTAINER.stop();
    }
}



//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@DirtiesContext
//public abstract class AbstractIt {
//
//    private static final int POSTGRES_PORT = 5432;
//
//    static final GenericContainer POSTGRES_CONTAINER = new GenericContainer("postgres:15.6-alpine")
//            .withEnv("POSTGRES_PASSWORD", "mydb111").withEnv("POSTGRES_DB", "spaceMarket")
//            .withExposedPorts(POSTGRES_PORT);
//
//    static {
//        POSTGRES_CONTAINER.start();
//    }
//
//    @RegisterExtension
//    static WireMockExtension wireMockServer = WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).configureStaticDsl(true).build();
//
//    @DynamicPropertySource
//    static void setupTestContainerProperties(DynamicPropertyRegistry registry) {
//        registry.add("application.payment-service.base-path", wireMockServer::baseUrl);
//        registry.add("spring.datasource.url", () -> format("jdbc:postgresql://%s:%d/spaceMarket",
//                POSTGRES_CONTAINER.getHost(), POSTGRES_CONTAINER.getMappedPort(POSTGRES_PORT)));
//        registry.add("spring.datasource.username", () -> "postgres");
//        registry.add("spring.datasource.password", () -> "mydb111");
//
//        WireMock.configureFor(wireMockServer.getPort());
//    }
//
//}
//
