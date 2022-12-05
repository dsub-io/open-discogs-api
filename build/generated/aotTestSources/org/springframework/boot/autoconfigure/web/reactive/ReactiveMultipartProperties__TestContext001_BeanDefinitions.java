package org.springframework.boot.autoconfigure.web.reactive;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ReactiveMultipartProperties}
 */
public class ReactiveMultipartProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'reactiveMultipartProperties'
   */
  public static BeanDefinition getReactiveMultipartPropertiesBeanDefinition() {
    Class<?> beanType = ReactiveMultipartProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ReactiveMultipartProperties::new);
    return beanDefinition;
  }
}
