package org.springframework.boot.autoconfigure.task;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Bean definitions for {@link TaskExecutionAutoConfiguration}
 */
public class TaskExecutionAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'taskExecutionAutoConfiguration'
   */
  public static BeanDefinition getTaskExecutionAutoConfigurationBeanDefinition() {
    Class<?> beanType = TaskExecutionAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(TaskExecutionAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'taskExecutorBuilder'.
   */
  private static BeanInstanceSupplier<TaskExecutorBuilder> getTaskExecutorBuilderInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<TaskExecutorBuilder>forFactoryMethod(TaskExecutionAutoConfiguration.class, "taskExecutorBuilder", TaskExecutionProperties.class, ObjectProvider.class, ObjectProvider.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(TaskExecutionAutoConfiguration.class).taskExecutorBuilder(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'taskExecutorBuilder'
   */
  public static BeanDefinition getTaskExecutorBuilderBeanDefinition() {
    Class<?> beanType = TaskExecutorBuilder.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getTaskExecutorBuilderInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'applicationTaskExecutor'.
   */
  private static BeanInstanceSupplier<ThreadPoolTaskExecutor> getApplicationTaskExecutorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ThreadPoolTaskExecutor>forFactoryMethod(TaskExecutionAutoConfiguration.class, "applicationTaskExecutor", TaskExecutorBuilder.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(TaskExecutionAutoConfiguration.class).applicationTaskExecutor(args.get(0)));
  }

  /**
   * Get the bean definition for 'applicationTaskExecutor'
   */
  public static BeanDefinition getApplicationTaskExecutorBeanDefinition() {
    Class<?> beanType = ThreadPoolTaskExecutor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setLazyInit(true);
    beanDefinition.setInstanceSupplier(getApplicationTaskExecutorInstanceSupplier());
    return beanDefinition;
  }
}
