package org.springframework.context.event;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link EventListenerMethodProcessor}
 */
public class EventListenerMethodProcessor__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'internalEventListenerProcessor'
   */
  public static BeanDefinition getInternalEventListenerProcessorBeanDefinition() {
    Class<?> beanType = EventListenerMethodProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(EventListenerMethodProcessor::new);
    return beanDefinition;
  }
}
