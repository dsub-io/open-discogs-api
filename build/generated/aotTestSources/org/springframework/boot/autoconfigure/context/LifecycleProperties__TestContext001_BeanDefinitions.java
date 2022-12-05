package org.springframework.boot.autoconfigure.context;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link LifecycleProperties}
 */
public class LifecycleProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'lifecycleProperties'
   */
  public static BeanDefinition getLifecyclePropertiesBeanDefinition() {
    Class<?> beanType = LifecycleProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(LifecycleProperties::new);
    return beanDefinition;
  }
}
