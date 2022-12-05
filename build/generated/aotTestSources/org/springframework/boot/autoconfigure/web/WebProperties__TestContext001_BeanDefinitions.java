package org.springframework.boot.autoconfigure.web;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link WebProperties}
 */
public class WebProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'webProperties'
   */
  public static BeanDefinition getWebPropertiesBeanDefinition() {
    Class<?> beanType = WebProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(WebProperties::new);
    return beanDefinition;
  }
}
