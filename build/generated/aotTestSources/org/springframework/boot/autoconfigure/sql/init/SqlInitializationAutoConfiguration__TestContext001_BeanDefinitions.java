package org.springframework.boot.autoconfigure.sql.init;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SqlInitializationAutoConfiguration}
 */
public class SqlInitializationAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'sqlInitializationAutoConfiguration'
   */
  public static BeanDefinition getSqlInitializationAutoConfigurationBeanDefinition() {
    Class<?> beanType = SqlInitializationAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(SqlInitializationAutoConfiguration::new);
    return beanDefinition;
  }
}
