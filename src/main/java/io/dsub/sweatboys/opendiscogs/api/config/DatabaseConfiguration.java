package io.dsub.sweatboys.opendiscogs.api.config;

import io.dsub.sweatboys.opendiscogs.api.config.properties.DatabaseProperties;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class DatabaseConfiguration {

  @Bean
  public ConnectionFactory connectionFactory(final DatabaseProperties properties) {
    System.out.printf("%s %s %s %s\n", properties.getDatabase(), properties.getHost(),
        properties.getUsername(), properties.getPassword());
    final ConnectionFactoryOptions options = getConnectionFactoryOptions(properties);
    return ConnectionFactoryBuilder.withOptions(options.mutate()).build();
  }

  private ConnectionFactoryOptions getConnectionFactoryOptions(
      final DatabaseProperties properties) {
    return ConnectionFactoryOptions.parse(properties.getUrl()).mutate()
        .option(ConnectionFactoryOptions.USER, properties.getUsername())
        .option(ConnectionFactoryOptions.PASSWORD, properties.getPassword())
        .build();
  }
}
