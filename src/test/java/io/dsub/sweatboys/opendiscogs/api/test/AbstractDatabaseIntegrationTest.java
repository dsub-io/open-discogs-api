package io.dsub.sweatboys.opendiscogs.api.test;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.blockhound.BlockHound;

@DataR2dbcTest
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
public abstract class AbstractDatabaseIntegrationTest {

  static {
    BlockHound.builder()
        .allowBlockingCallsInside("io.r2dbc.spi.ConnectionFactory", "find")
        .install();
  }

  @BeforeAll
  static void beforeAll(@Autowired ConnectionFactory factory) {
    var populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("schema.sql"));
    populator.populate(factory).block();
  }
}
