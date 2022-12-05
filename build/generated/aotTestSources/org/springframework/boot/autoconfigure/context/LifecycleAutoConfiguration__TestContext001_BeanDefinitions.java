package org.springframework.boot.autoconfigure.context;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.DefaultLifecycleProcessor;

/**
 * Bean definitions for {@link LifecycleAutoConfiguration}
 */
public class LifecycleAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'lifecycleAutoConfiguration'
   */
  public static BeanDefinition getLifecycleAutoConfigurationBeanDefinition() {
    Class<?> beanType = LifecycleAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(LifecycleAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'lifecycleProcessor'.
   */
  private static BeanInstanceSupplier<DefaultLifecycleProcessor> getLifecycleProcessorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<DefaultLifecycleProcessor>forFactoryMethod(LifecycleAutoConfiguration.class, "defaultLifecycleProcessor", LifecycleProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(LifecycleAutoConfiguration.class).defaultLifecycleProcessor(args.get(0)));
  }

  /**
   * Get the bean definition for 'lifecycleProcessor'
   */
  public static BeanDefinition getLifecycleProcessorBeanDefinition() {
    Class<?> beanType = DefaultLifecycleProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getLifecycleProcessorInstanceSupplier());
    return beanDefinition;
  }
}
