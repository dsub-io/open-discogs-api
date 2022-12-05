package org.springframework.boot.autoconfigure.web.reactive.function.client;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.http.client.reactive.ClientHttpConnector;

/**
 * Bean definitions for {@link ClientHttpConnectorAutoConfiguration}
 */
public class ClientHttpConnectorAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'clientHttpConnectorAutoConfiguration'
   */
  public static BeanDefinition getClientHttpConnectorAutoConfigurationBeanDefinition() {
    Class<?> beanType = ClientHttpConnectorAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ClientHttpConnectorAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientConnectorCustomizer'.
   */
  private static BeanInstanceSupplier<WebClientCustomizer> getClientConnectorCustomizerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<WebClientCustomizer>forFactoryMethod(ClientHttpConnectorAutoConfiguration.class, "clientConnectorCustomizer", ClientHttpConnector.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ClientHttpConnectorAutoConfiguration.class).clientConnectorCustomizer(args.get(0)));
  }

  /**
   * Get the bean definition for 'clientConnectorCustomizer'
   */
  public static BeanDefinition getClientConnectorCustomizerBeanDefinition() {
    Class<?> beanType = WebClientCustomizer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setLazyInit(true);
    beanDefinition.setInstanceSupplier(getClientConnectorCustomizerInstanceSupplier());
    return beanDefinition;
  }
}
