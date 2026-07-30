package io.dsub.opendiscogs.api.test;

import io.dsub.opendiscogs.api.config.JooqConfiguration;
import io.dsub.opendiscogs.api.test.AbstractDatabaseIntegrationTest.TestConfig;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Import({TestConfig.class, JooqConfiguration.class})
@Tag("integration")
@DataR2dbcTest
public abstract class AbstractDatabaseIntegrationTest {

  private static final PostgreSQLContainer POSTGRES =
      new PostgreSQLContainer("postgres:18.4-alpine")
          .withDatabaseName("discogs");

  static {
    POSTGRES.start();
  }

  @DynamicPropertySource
  static void databaseProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.r2dbc.url",
        () -> "r2dbc:postgresql://%s:%d/%s".formatted(
            POSTGRES.getHost(),
            POSTGRES.getFirstMappedPort(),
            POSTGRES.getDatabaseName()));
    registry.add("spring.r2dbc.username", POSTGRES::getUsername);
    registry.add("spring.r2dbc.password", POSTGRES::getPassword);
  }

  protected void initDatabase(DatabaseClient client) {
    client.sql("DROP schema public CASCADE; CREATE SCHEMA public").then().block();
    new ResourceDatabasePopulator(new ClassPathResource("postgresql-init.sql")).populate(
        client.getConnectionFactory()).block();
  }

  @TestConfiguration
  public static class TestConfig {

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
      ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
      initializer.setConnectionFactory(connectionFactory);
      CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
      populator.addPopulators(
          new ResourceDatabasePopulator(new ClassPathResource("postgresql-init.sql")));
      initializer.setDatabasePopulator(populator);
      return initializer;
    }
  }
}
