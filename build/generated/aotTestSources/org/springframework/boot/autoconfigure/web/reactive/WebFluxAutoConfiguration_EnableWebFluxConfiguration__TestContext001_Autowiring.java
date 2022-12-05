package org.springframework.boot.autoconfigure.web.reactive;

import java.util.List;
import org.springframework.beans.factory.aot.AutowiredMethodArgumentsResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link WebFluxAutoConfiguration.EnableWebFluxConfiguration}.
 */
public class WebFluxAutoConfiguration_EnableWebFluxConfiguration__TestContext001_Autowiring {
  /**
   * Apply the autowiring.
   */
  public static WebFluxAutoConfiguration.EnableWebFluxConfiguration apply(
      RegisteredBean registeredBean, WebFluxAutoConfiguration.EnableWebFluxConfiguration instance) {
    AutowiredMethodArgumentsResolver.forMethod("setConfigurers", List.class).resolve(registeredBean, args -> instance.setConfigurers(args.get(0)));
    return instance;
  }
}
