package io.dsub.sweatboys.opendiscogs.api.config;

import io.dsub.sweatboys.opendiscogs.api.config.properties.DatabaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@EnableConfigurationProperties
public class ApplicationPropertiesConfig {
  @Bean
  @Validated
  @ConfigurationProperties("api.db")
  public DatabaseProperties databaseProperties() {
    return new DatabaseProperties();
  }
}
