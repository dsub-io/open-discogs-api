package org.springframework.boot.context.properties;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ConfigurationPropertiesBindingPostProcessor}
 */
public class ConfigurationPropertiesBindingPostProcessor__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'configurationPropertiesBindingPostProcessor'
   */
  public static BeanDefinition getConfigurationPropertiesBindingPostProcessorBeanDefinition() {
    Class<?> beanType = ConfigurationPropertiesBindingPostProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(ConfigurationPropertiesBindingPostProcessor::new);
    return beanDefinition;
  }
}
