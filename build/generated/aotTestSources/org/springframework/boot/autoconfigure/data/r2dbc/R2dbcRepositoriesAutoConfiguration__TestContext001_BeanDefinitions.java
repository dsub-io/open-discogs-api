package org.springframework.boot.autoconfigure.data.r2dbc;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link R2dbcRepositoriesAutoConfiguration}
 */
public class R2dbcRepositoriesAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'r2dbcRepositoriesAutoConfiguration'
   */
  public static BeanDefinition getRdbcRepositoriesAutoConfigurationBeanDefinition() {
    Class<?> beanType = R2dbcRepositoriesAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(R2dbcRepositoriesAutoConfiguration::new);
    return beanDefinition;
  }
}
