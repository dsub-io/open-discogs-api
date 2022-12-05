package org.springframework.boot.autoconfigure.jdbc;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataSourceTransactionManagerAutoConfiguration}
 */
public class DataSourceTransactionManagerAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'dataSourceTransactionManagerAutoConfiguration'
   */
  public static BeanDefinition getDataSourceTransactionManagerAutoConfigurationBeanDefinition() {
    Class<?> beanType = DataSourceTransactionManagerAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(DataSourceTransactionManagerAutoConfiguration::new);
    return beanDefinition;
  }
}
