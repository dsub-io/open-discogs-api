package io.dsub.opendiscogs.api.config;

import io.dsub.opendiscogs.api.config.properties.DatabaseProperties;
import java.net.URI;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
public class ApplicationPropertiesConfiguration {

  private static volatile String serverUrl = "http://localhost:8080";

  public static String getServerUrl() {
    return serverUrl;
  }

  @Value("${api.server.url:http://localhost:8080}")
  public void applyServerUrl(String value) {
    var candidate = URI.create(value.trim());
    var scheme = candidate.getScheme();
    if (scheme == null
        || (!scheme.toLowerCase(Locale.ROOT).equals("http")
        && !scheme.toLowerCase(Locale.ROOT).equals("https"))
        || candidate.getHost() == null) {
      throw new IllegalArgumentException("api.server.url must be an absolute HTTP(S) URL");
    }
    serverUrl = value.trim().replaceFirst("/+$", "");
  }

  @Bean
  @Validated
  @ConfigurationProperties("api.db")
  public DatabaseProperties databaseProperties() {
    return new DatabaseProperties();
  }
}
