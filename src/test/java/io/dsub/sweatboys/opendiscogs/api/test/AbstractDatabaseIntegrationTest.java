package io.dsub.sweatboys.opendiscogs.api.test;

import io.dsub.sweatboys.opendiscogs.api.test.AbstractDatabaseIntegrationTest.TestConfig;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Import(TestConfig.class)
@DataR2dbcTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
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
}
