package org.springframework.transaction.annotation;

import java.util.Collection;
import org.springframework.beans.factory.aot.AutowiredMethodArgumentsResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ProxyTransactionManagementConfiguration}.
 */
public class ProxyTransactionManagementConfiguration__TestContext001_Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ProxyTransactionManagementConfiguration apply(RegisteredBean registeredBean,
      ProxyTransactionManagementConfiguration instance) {
    AutowiredMethodArgumentsResolver.forMethod("setConfigurers", Collection.class).resolve(registeredBean, args -> instance.setConfigurers(args.get(0)));
    return instance;
  }
}
