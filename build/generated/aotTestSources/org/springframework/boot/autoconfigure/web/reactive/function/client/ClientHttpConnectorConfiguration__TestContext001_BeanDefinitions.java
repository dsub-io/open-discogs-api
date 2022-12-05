package org.springframework.boot.autoconfigure.web.reactive.function.client;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;

/**
 * Bean definitions for {@link ClientHttpConnectorConfiguration}
 */
public class ClientHttpConnectorConfiguration__TestContext001_BeanDefinitions {
  /**
   * Bean definitions for {@link ClientHttpConnectorConfiguration.ReactorNetty}
   */
  public static class ReactorNetty__BeanDefinitions {
    /**
     * Get the bean definition for 'reactorNetty'
     */
    public static BeanDefinition getReactorNettyBeanDefinition() {
      Class<?> beanType = ClientHttpConnectorConfiguration.ReactorNetty.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(ClientHttpConnectorConfiguration.ReactorNetty::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'reactorClientHttpConnector'.
     */
    private static BeanInstanceSupplier<ReactorClientHttpConnector> getReactorClientHttpConnectorInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<ReactorClientHttpConnector>forFactoryMethod(ClientHttpConnectorConfiguration.ReactorNetty.class, "reactorClientHttpConnector", ReactorResourceFactory.class, ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ClientHttpConnectorConfiguration.ReactorNetty.class).reactorClientHttpConnector(args.get(0), args.get(1)));
    }

    /**
     * Get the bean definition for 'reactorClientHttpConnector'
     */
    public static BeanDefinition getReactorClientHttpConnectorBeanDefinition() {
      Class<?> beanType = ReactorClientHttpConnector.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setLazyInit(true);
      beanDefinition.setInstanceSupplier(getReactorClientHttpConnectorInstanceSupplier());
      return beanDefinition;
    }
  }
}
