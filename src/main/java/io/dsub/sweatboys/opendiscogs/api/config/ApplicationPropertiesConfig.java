package io.dsub.sweatboys.opendiscogs.api.config;

import io.dsub.sweatboys.opendiscogs.api.config.properties.DatabaseProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
public class ApplicationPropertiesConfig {

  @Value("${API.SERVER.HOST}")
  public void setHostName(String host) {
    HOST_ADDRESS = host;
  }

  private static String HOST_ADDRESS;

  @Bean
  @Validated
  @ConfigurationProperties("api.db")
  public DatabaseProperties databaseProperties() {
    return new DatabaseProperties();
  }

  public static String getServerAddress() {
    return HOST_ADDRESS;
  }
}
