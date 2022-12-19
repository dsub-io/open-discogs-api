package io.dsub.sweatboys.opendiscogs.api.config;

import io.dsub.sweatboys.opendiscogs.api.test.ConcurrentTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;

@WebFluxTest
@ContextConfiguration(classes = PageableWebFluxConfiguration.class)
class PageableWebFluxConfigurationTest extends ConcurrentTest {

  @Test
  void contextTest() {
  }
}
