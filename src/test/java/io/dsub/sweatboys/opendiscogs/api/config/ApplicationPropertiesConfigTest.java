package io.dsub.sweatboys.opendiscogs.api.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.dsub.sweatboys.opendiscogs.api.config.properties.DatabaseProperties;
import io.dsub.sweatboys.opendiscogs.api.test.ConcurrentTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationPropertiesConfig.class)
@TestPropertySource(properties = {
    "api.db.username=testUsername",
    "api.db.password=testPassword",
    "api.db.database=testDatabase",
    "api.db.host=testHost"
})
class ApplicationPropertiesConfigTest extends ConcurrentTest {
  @Autowired
  private DatabaseProperties properties;

  @Test
  void databasePropertiesMustBeSet() {
    assertThat(properties).isNotNull();
    assertThat(properties.getDatabase()).isEqualTo("testDatabase");
    assertThat(properties.getUsername()).isEqualTo("testUsername");
    assertThat(properties.getPassword()).isEqualTo("testPassword");
    assertThat(properties.getHost()).isEqualTo("testHost");
  }
}