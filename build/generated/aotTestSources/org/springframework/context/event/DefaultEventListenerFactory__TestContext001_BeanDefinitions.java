package org.springframework.context.event;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DefaultEventListenerFactory}
 */
public class DefaultEventListenerFactory__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'internalEventListenerFactory'
   */
  public static BeanDefinition getInternalEventListenerFactoryBeanDefinition() {
    Class<?> beanType = DefaultEventListenerFactory.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(DefaultEventListenerFactory::new);
    return beanDefinition;
  }
}
