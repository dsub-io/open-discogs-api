package org.springframework.boot.autoconfigure.jackson;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link JacksonProperties}
 */
public class JacksonProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'jacksonProperties'
   */
  public static BeanDefinition getJacksonPropertiesBeanDefinition() {
    Class<?> beanType = JacksonProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(JacksonProperties::new);
    return beanDefinition;
  }
}
