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

  private static String HOST_ADDRESS;

  public static String getServerAddress() {
    return HOST_ADDRESS;
  }

  @Value("${API.SERVER.HOST}")
  public void setHostName(String host) {
    HOST_ADDRESS = host;
  }

  @Bean
  @Validated
  @ConfigurationProperties("api.db")
  public DatabaseProperties databaseProperties() {
    return new DatabaseProperties();
  }
}
