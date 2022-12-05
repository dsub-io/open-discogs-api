package org.springframework.boot.autoconfigure.r2dbc;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link R2dbcAutoConfiguration}
 */
public class R2dbcAutoConfiguration__TestContext002_BeanDefinitions {
  /**
   * Get the bean definition for 'r2dbcAutoConfiguration'
   */
  public static BeanDefinition getRdbcAutoConfigurationBeanDefinition() {
    Class<?> beanType = R2dbcAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(R2dbcAutoConfiguration::new);
    return beanDefinition;
  }
}
