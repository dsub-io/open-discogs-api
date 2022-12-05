package org.springframework.boot.autoconfigure.sql.init;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SqlInitializationProperties}
 */
public class SqlInitializationProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'sqlInitializationProperties'
   */
  public static BeanDefinition getSqlInitializationPropertiesBeanDefinition() {
    Class<?> beanType = SqlInitializationProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(SqlInitializationProperties::new);
    return beanDefinition;
  }
}
