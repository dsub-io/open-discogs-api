package org.springframework.boot.autoconfigure.task;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TaskExecutionProperties}
 */
public class TaskExecutionProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'taskExecutionProperties'
   */
  public static BeanDefinition getTaskExecutionPropertiesBeanDefinition() {
    Class<?> beanType = TaskExecutionProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(TaskExecutionProperties::new);
    return beanDefinition;
  }
}
