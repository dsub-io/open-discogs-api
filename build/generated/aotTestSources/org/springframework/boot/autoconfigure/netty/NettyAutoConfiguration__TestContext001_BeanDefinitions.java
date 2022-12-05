package org.springframework.boot.autoconfigure.netty;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link NettyAutoConfiguration}
 */
public class NettyAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.netty.NettyAutoConfiguration'.
   */
  private static BeanInstanceSupplier<NettyAutoConfiguration> getNettyAutoConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<NettyAutoConfiguration>forConstructor(NettyProperties.class)
            .withGenerator((registeredBean, args) -> new NettyAutoConfiguration(args.get(0)));
  }

  /**
   * Get the bean definition for 'nettyAutoConfiguration'
   */
  public static BeanDefinition getNettyAutoConfigurationBeanDefinition() {
    Class<?> beanType = NettyAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getNettyAutoConfigurationInstanceSupplier());
    return beanDefinition;
  }
}
