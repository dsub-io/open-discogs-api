package io.dsub.opendiscogs.api;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Tag("e2e")
@AutoConfigureWebTestClient
@Import(OpenDiscogsApiE2ETest.DatabaseInitializer.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "api.server.url=http://localhost:8080",
        "management.server.port=0"
    })
class OpenDiscogsApiE2ETest {

  private static final PostgreSQLContainer POSTGRES =
      new PostgreSQLContainer("postgres:18.4-alpine")
          .withDatabaseName("discogs");

  static {
    POSTGRES.start();
  }

  @Autowired
  DatabaseClient databaseClient;

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  BuildProperties buildProperties;

  @DynamicPropertySource
  static void databaseProperties(DynamicPropertyRegistry registry) {
    registry.add("api.db.host",
        () -> "%s:%d".formatted(POSTGRES.getHost(), POSTGRES.getFirstMappedPort()));
    registry.add("api.db.database", POSTGRES::getDatabaseName);
    registry.add("api.db.username", POSTGRES::getUsername);
    registry.add("api.db.password", POSTGRES::getPassword);
  }

  @BeforeEach
  void seedRelease() {
    databaseClient.sql("TRUNCATE TABLE release_item RESTART IDENTITY CASCADE")
        .then()
        .block();
    databaseClient.sql("""
            INSERT INTO release_item
              (id, created_at, last_modified_at, country, data_quality,
               has_valid_day, has_valid_month, has_valid_year, is_master,
               listed_release_date, notes, release_date, status, title)
            VALUES
              (1, NOW(), NOW(), 'KR', 'Correct',
               TRUE, TRUE, TRUE, FALSE,
               '2026-07-31', 'e2e notes', DATE '2026-07-31', 'Accepted',
               'E2E Release')
            """)
        .then()
        .block();
  }

  @Test
  void servesReleaseSearchAndDetailThroughTheCompleteApplication() {
    webTestClient.get()
        .uri("/releases?page=1&size=10&title=e2e")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.total_elements").isEqualTo(1)
        .jsonPath("$.items[0].id").isEqualTo(1)
        .jsonPath("$.items[0].title").isEqualTo("E2E Release")
        .jsonPath("$.items[0].released_year").isEqualTo(2026);

    webTestClient.get()
        .uri("/releases/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.id").isEqualTo(1)
        .jsonPath("$.artists.length()").isEqualTo(0)
        .jsonPath("$.labels.length()").isEqualTo(0);
  }

  @Test
  void publishesTheReleaseVersionInOpenApi() {
    webTestClient.get()
        .uri("/v3/api-docs")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.info.title").isEqualTo("OpenDiscogs API")
        .jsonPath("$.info.version").isEqualTo(buildProperties.getVersion())
        .jsonPath("$.servers[0].url").isEqualTo("http://localhost:8080");
  }

  @TestConfiguration(proxyBeanMethods = false)
  static class DatabaseInitializer {

    @Bean
    ConnectionFactoryInitializer databaseInitializer(ConnectionFactory connectionFactory) {
      var initializer = new ConnectionFactoryInitializer();
      initializer.setConnectionFactory(connectionFactory);
      initializer.setDatabasePopulator(
          new ResourceDatabasePopulator(new ClassPathResource("postgresql-init.sql")));
      return initializer;
    }
  }
}
