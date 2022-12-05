package io.dsub.sweatboys.opendiscogs.api.config;

import io.dsub.sweatboys.opendiscogs.api.config.properties.DatabaseProperties;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfiguration {
    @Bean
    public DatabaseProperties databaseProperties(Environment env) {
        return new DatabaseProperties(env);
    }
    @Bean
    public ConnectionFactory connectionFactory(DatabaseProperties properties) {
        final ConnectionFactoryOptions options = getConnectionFactoryOptions(properties);
        return ConnectionFactories.get(options);
    }
    private ConnectionFactoryOptions getConnectionFactoryOptions(DatabaseProperties properties) {
        return ConnectionFactoryOptions.parse(properties.getUrl()).mutate()
                .option(ConnectionFactoryOptions.USER, properties.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, properties.getPassword())
                .build();
    }
}
