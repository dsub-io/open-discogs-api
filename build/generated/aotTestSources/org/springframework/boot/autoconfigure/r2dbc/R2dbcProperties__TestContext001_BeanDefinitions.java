package org.springframework.boot.autoconfigure.r2dbc;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link R2dbcProperties}
 */
public class R2dbcProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'r2dbcProperties'
   */
  public static BeanDefinition getRdbcPropertiesBeanDefinition() {
    Class<?> beanType = R2dbcProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(R2dbcProperties::new);
    return beanDefinition;
  }
}
