package io.dsub.sweatboys.opendiscogs.api.config;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationPropertiesConfig.class, DatabaseConfiguration.class})
@TestPropertySource(properties = {
        "api.db.username=testUsername",
        "api.db.password=testPassword",
        "api.db.database=testDatabase",
        "api.db.host=testHost"
})
public class DatabaseConfigurationTest {

    @Test
    void TestContextLoads(@Autowired ConnectionFactory connectionFactory) {
        assertThat(connectionFactory).isNotNull();
    }
}
