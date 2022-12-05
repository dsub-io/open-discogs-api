package org.springframework.boot.autoconfigure.context;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ConfigurationPropertiesAutoConfiguration}
 */
public class ConfigurationPropertiesAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'configurationPropertiesAutoConfiguration'
   */
  public static BeanDefinition getConfigurationPropertiesAutoConfigurationBeanDefinition() {
    Class<?> beanType = ConfigurationPropertiesAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ConfigurationPropertiesAutoConfiguration::new);
    return beanDefinition;
  }
}
