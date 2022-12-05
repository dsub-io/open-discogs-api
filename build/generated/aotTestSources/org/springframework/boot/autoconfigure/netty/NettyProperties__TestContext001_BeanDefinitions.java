package org.springframework.boot.autoconfigure.netty;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link NettyProperties}
 */
public class NettyProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'nettyProperties'
   */
  public static BeanDefinition getNettyPropertiesBeanDefinition() {
    Class<?> beanType = NettyProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(NettyProperties::new);
    return beanDefinition;
  }
}
