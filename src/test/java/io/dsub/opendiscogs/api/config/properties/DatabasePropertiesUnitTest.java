package io.dsub.opendiscogs.api.config.properties;

import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.opendiscogs.api.test.ConcurrentTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabasePropertiesUnitTest extends ConcurrentTest {

  private DatabaseProperties properties;

  @BeforeEach
  void setUp() {
    properties = new DatabaseProperties();
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getUrlReturnsValidPostgresqlR2dbcUrl() {
    properties.setDatabase("test");
    properties.setHost("localhost:5432");
    var url = properties.getUrl();
    assertThat(url)
        .isNotNull()
        .isNotBlank()
        .isEqualTo("r2dbc:postgresql://localhost:5432/test");
  }

  @Test
  void usernameMustRemainUnmodified() {
    properties.setUsername("test!some#where@@");
    assertThat(properties.getUsername())
        .isEqualTo("test!some#where@@");
  }

  @Test
  void passwordMustRemainUnmodified() {
    properties.setPassword("test!some#pass@word@");
    assertThat(properties.getPassword())
        .isEqualTo("test!some#pass@word@");
  }


  @Test
  void usernameMustHandleEmptyOrNullString() {
    properties.setUsername("");
    assertThat(properties.getUsername()).isNotNull().isEmpty();
    properties.setUsername(" ");
    assertThat(properties.getUsername()).isEqualTo(" ");
    properties.setUsername(null);
    assertThat(properties.getUsername()).isNull();
  }

  @Test
  void passwordMustHandleEmptyOrNullString() {
    properties.setPassword("");
    assertThat(properties.getPassword()).isNotNull().isEmpty();
    properties.setPassword(" ");
    assertThat(properties.getPassword()).isEqualTo(" ");
    properties.setPassword(null);
    assertThat(properties.getPassword()).isNull();
  }
}
