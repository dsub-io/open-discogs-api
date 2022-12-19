package io.dsub.sweatboys.opendiscogs.api.test;

import io.dsub.sweatboys.opendiscogs.api.config.JooqConfiguration;
import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest.TestConfig;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@Import({TestConfig.class, JooqConfiguration.class})
@Testcontainers
@DataR2dbcTest(properties = "spring.r2dbc.url=r2dbc:tc:postgresql:///discogs?TC_IMAGE_TAG=14.5-alpine")
public abstract class AbstractDatabaseIntegrationTest {
  @TestConfiguration
  public static class TestConfig {
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
      ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
      initializer.setConnectionFactory(connectionFactory);
      CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
      populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
      initializer.setDatabasePopulator(populator);
      return initializer;
    }
  }

  protected void initDatabase(DatabaseClient client){
    client.sql("DROP schema public CASCADE; CREATE SCHEMA public").then().block();
    new ResourceDatabasePopulator(new ClassPathResource("schema.sql")).populate(client.getConnectionFactory()).block();
  }
}
