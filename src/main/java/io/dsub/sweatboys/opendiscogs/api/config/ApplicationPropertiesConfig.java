package io.dsub.sweatboys.opendiscogs.api.config;

import io.dsub.sweatboys.opendiscogs.api.config.properties.DatabaseProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
public class ApplicationPropertiesConfig {

  private static String SERVER_URL;
  public static String getServerUrl() {
    return SERVER_URL;
  }

  @Value("${API.SERVER.URL:localhost:8080}")
  public void applyServerUrl(String host) {
    setServerUrl(host);
  }

  private static void setServerUrl(String host) {
    SERVER_URL = host;
  }
  @Bean
  @Validated
  @ConfigurationProperties("api.db")
  public DatabaseProperties databaseProperties() {
    return new DatabaseProperties();
  }
}
