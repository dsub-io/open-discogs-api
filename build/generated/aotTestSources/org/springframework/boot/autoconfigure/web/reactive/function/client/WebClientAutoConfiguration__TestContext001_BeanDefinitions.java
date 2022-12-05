package org.springframework.boot.autoconfigure.web.reactive.function.client;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Bean definitions for {@link WebClientAutoConfiguration}
 */
public class WebClientAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'webClientAutoConfiguration'
   */
  public static BeanDefinition getWebClientAutoConfigurationBeanDefinition() {
    Class<?> beanType = WebClientAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(WebClientAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'webClientBuilder'.
   */
  private static BeanInstanceSupplier<WebClient.Builder> getWebClientBuilderInstanceSupplier() {
    return BeanInstanceSupplier.<WebClient.Builder>forFactoryMethod(WebClientAutoConfiguration.class, "webClientBuilder", ObjectProvider.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(WebClientAutoConfiguration.class).webClientBuilder(args.get(0)));
  }

  /**
   * Get the bean definition for 'webClientBuilder'
   */
  public static BeanDefinition getWebClientBuilderBeanDefinition() {
    Class<?> beanType = WebClient.Builder.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getWebClientBuilderInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link WebClientAutoConfiguration.WebClientCodecsConfiguration}
   */
  public static class WebClientCodecsConfiguration__BeanDefinitions {
    /**
     * Get the bean definition for 'webClientCodecsConfiguration'
     */
    public static BeanDefinition getWebClientCodecsConfigurationBeanDefinition() {
      Class<?> beanType = WebClientAutoConfiguration.WebClientCodecsConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(WebClientAutoConfiguration.WebClientCodecsConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'exchangeStrategiesCustomizer'.
     */
    private static BeanInstanceSupplier<WebClientCodecCustomizer> getExchangeStrategiesCustomizerInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<WebClientCodecCustomizer>forFactoryMethod(WebClientAutoConfiguration.WebClientCodecsConfiguration.class, "exchangeStrategiesCustomizer", ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(WebClientAutoConfiguration.WebClientCodecsConfiguration.class).exchangeStrategiesCustomizer(args.get(0)));
    }

    /**
     * Get the bean definition for 'exchangeStrategiesCustomizer'
     */
    public static BeanDefinition getExchangeStrategiesCustomizerBeanDefinition() {
      Class<?> beanType = WebClientCodecCustomizer.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getExchangeStrategiesCustomizerInstanceSupplier());
      return beanDefinition;
    }
  }
}
