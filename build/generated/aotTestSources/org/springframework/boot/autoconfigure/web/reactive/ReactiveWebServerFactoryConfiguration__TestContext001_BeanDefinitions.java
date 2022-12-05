package org.springframework.boot.autoconfigure.web.reactive;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.http.client.reactive.ReactorResourceFactory;

/**
 * Bean definitions for {@link ReactiveWebServerFactoryConfiguration}
 */
public class ReactiveWebServerFactoryConfiguration__TestContext001_BeanDefinitions {
  /**
   * Bean definitions for {@link ReactiveWebServerFactoryConfiguration.EmbeddedNetty}
   */
  public static class EmbeddedNetty__BeanDefinitions {
    /**
     * Get the bean definition for 'embeddedNetty'
     */
    public static BeanDefinition getEmbeddedNettyBeanDefinition() {
      Class<?> beanType = ReactiveWebServerFactoryConfiguration.EmbeddedNetty.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(ReactiveWebServerFactoryConfiguration.EmbeddedNetty::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'reactorServerResourceFactory'.
     */
    private static BeanInstanceSupplier<ReactorResourceFactory> getReactorServerResourceFactoryInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<ReactorResourceFactory>forFactoryMethod(ReactiveWebServerFactoryConfiguration.EmbeddedNetty.class, "reactorServerResourceFactory")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(ReactiveWebServerFactoryConfiguration.EmbeddedNetty.class).reactorServerResourceFactory());
    }

    /**
     * Get the bean definition for 'reactorServerResourceFactory'
     */
    public static BeanDefinition getReactorServerResourceFactoryBeanDefinition() {
      Class<?> beanType = ReactorResourceFactory.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getReactorServerResourceFactoryInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'nettyReactiveWebServerFactory'.
     */
    private static BeanInstanceSupplier<NettyReactiveWebServerFactory> getNettyReactiveWebServerFactoryInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<NettyReactiveWebServerFactory>forFactoryMethod(ReactiveWebServerFactoryConfiguration.EmbeddedNetty.class, "nettyReactiveWebServerFactory", ReactorResourceFactory.class, ObjectProvider.class, ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ReactiveWebServerFactoryConfiguration.EmbeddedNetty.class).nettyReactiveWebServerFactory(args.get(0), args.get(1), args.get(2)));
    }

    /**
     * Get the bean definition for 'nettyReactiveWebServerFactory'
     */
    public static BeanDefinition getNettyReactiveWebServerFactoryBeanDefinition() {
      Class<?> beanType = NettyReactiveWebServerFactory.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getNettyReactiveWebServerFactoryInstanceSupplier());
      return beanDefinition;
    }
  }
}
