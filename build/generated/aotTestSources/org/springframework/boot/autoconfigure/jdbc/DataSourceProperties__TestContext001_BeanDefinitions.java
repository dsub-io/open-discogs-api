package org.springframework.boot.autoconfigure.jdbc;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataSourceProperties}
 */
public class DataSourceProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'dataSourceProperties'
   */
  public static BeanDefinition getDataSourcePropertiesBeanDefinition() {
    Class<?> beanType = DataSourceProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(DataSourceProperties::new);
    return beanDefinition;
  }
}
