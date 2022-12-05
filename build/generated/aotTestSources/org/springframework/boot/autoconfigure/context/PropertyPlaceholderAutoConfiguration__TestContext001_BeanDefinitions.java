package org.springframework.boot.autoconfigure.context;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Bean definitions for {@link PropertyPlaceholderAutoConfiguration}
 */
public class PropertyPlaceholderAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'propertyPlaceholderAutoConfiguration'
   */
  public static BeanDefinition getPropertyPlaceholderAutoConfigurationBeanDefinition() {
    Class<?> beanType = PropertyPlaceholderAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(PropertyPlaceholderAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean definition for 'propertySourcesPlaceholderConfigurer'
   */
  public static BeanDefinition getPropertySourcesPlaceholderConfigurerBeanDefinition() {
    Class<?> beanType = PropertySourcesPlaceholderConfigurer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<PropertySourcesPlaceholderConfigurer>forFactoryMethod(PropertyPlaceholderAutoConfiguration.class, "propertySourcesPlaceholderConfigurer").withGenerator(PropertyPlaceholderAutoConfiguration::propertySourcesPlaceholderConfigurer));
    return beanDefinition;
  }
}
