package org.springframework.boot.context.properties;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link BoundConfigurationProperties}
 */
public class BoundConfigurationProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'boundConfigurationProperties'
   */
  public static BeanDefinition getBoundConfigurationPropertiesBeanDefinition() {
    Class<?> beanType = BoundConfigurationProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(BoundConfigurationProperties::new);
    return beanDefinition;
  }
}
