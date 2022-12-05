package org.springframework.boot.autoconfigure.task;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TaskSchedulingProperties}
 */
public class TaskSchedulingProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'taskSchedulingProperties'
   */
  public static BeanDefinition getTaskSchedulingPropertiesBeanDefinition() {
    Class<?> beanType = TaskSchedulingProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(TaskSchedulingProperties::new);
    return beanDefinition;
  }
}
