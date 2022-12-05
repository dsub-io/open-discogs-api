package org.springframework.boot.autoconfigure.web;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ServerProperties}
 */
public class ServerProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'serverProperties'
   */
  public static BeanDefinition getServerPropertiesBeanDefinition() {
    Class<?> beanType = ServerProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ServerProperties::new);
    return beanDefinition;
  }
}
