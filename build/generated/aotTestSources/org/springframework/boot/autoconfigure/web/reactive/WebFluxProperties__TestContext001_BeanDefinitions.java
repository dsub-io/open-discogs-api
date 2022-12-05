package org.springframework.boot.autoconfigure.web.reactive;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link WebFluxProperties}
 */
public class WebFluxProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'webFluxProperties'
   */
  public static BeanDefinition getWebFluxPropertiesBeanDefinition() {
    Class<?> beanType = WebFluxProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(WebFluxProperties::new);
    return beanDefinition;
  }
}
