package io.dsub.sweatboys.opendiscogs.api.config.properties;

import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.sweatboys.opendiscogs.api.test.ConcurrentTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabasePropertiesTest extends ConcurrentTest {

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
        .isEqualTo("r2dbc:postgres://localhost:5432/test");
  }

  @Test
  void usernameMustBeUTF8Encoded() {
    properties.setUsername("test!some#where@@");
    assertThat(properties.getUsername())
        .isEqualTo("test%21some%23where%40%40");
  }

  @Test
  void passwordMustBeUTF8Encoded() {
    properties.setPassword("test!some#pass@word@");
    assertThat(properties.getPassword())
        .isEqualTo("test%21some%23pass%40word%40");
  }


  @Test
  void usernameMustHandleEmptyOrNullString() {
    properties.setUsername("");
    assertThat(properties.getUsername()).isNotNull().isEmpty();
    properties.setUsername(" ");
    assertThat(properties.getUsername()).isNotNull().isEmpty();
    properties.setUsername(null);
    assertThat(properties.getUsername()).isNotNull().isEmpty();
  }

  @Test
  void passwordMustHandleEmptyOrNullString() {
    properties.setPassword("");
    assertThat(properties.getPassword()).isNotNull().isEmpty();
    properties.setPassword(" ");
    assertThat(properties.getPassword()).isNotNull().isEmpty();
    properties.setPassword(null);
    assertThat(properties.getPassword()).isNotNull().isEmpty();
  }
}
