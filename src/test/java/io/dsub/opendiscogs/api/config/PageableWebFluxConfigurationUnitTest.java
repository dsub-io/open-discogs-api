package io.dsub.opendiscogs.api.config;

import io.dsub.opendiscogs.api.test.ConcurrentTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;

@WebFluxTest
@ContextConfiguration(classes = PageableWebFluxConfiguration.class)
class PageableWebFluxConfigurationUnitTest extends ConcurrentTest {

  @Test
  void contextTest() {
  }
}
