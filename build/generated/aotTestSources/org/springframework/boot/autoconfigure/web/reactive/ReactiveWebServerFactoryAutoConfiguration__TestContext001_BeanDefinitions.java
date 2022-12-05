package org.springframework.boot.autoconfigure.web.reactive;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/**
 * Bean definitions for {@link ReactiveWebServerFactoryAutoConfiguration}
 */
public class ReactiveWebServerFactoryAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'reactiveWebServerFactoryAutoConfiguration'
   */
  public static BeanDefinition getReactiveWebServerFactoryAutoConfigurationBeanDefinition() {
    Class<?> beanType = ReactiveWebServerFactoryAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ReactiveWebServerFactoryAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'reactiveWebServerFactoryCustomizer'.
   */
  private static BeanInstanceSupplier<ReactiveWebServerFactoryCustomizer> getReactiveWebServerFactoryCustomizerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ReactiveWebServerFactoryCustomizer>forFactoryMethod(ReactiveWebServerFactoryAutoConfiguration.class, "reactiveWebServerFactoryCustomizer", ServerProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ReactiveWebServerFactoryAutoConfiguration.class).reactiveWebServerFactoryCustomizer(args.get(0)));
  }

  /**
   * Get the bean definition for 'reactiveWebServerFactoryCustomizer'
   */
  public static BeanDefinition getReactiveWebServerFactoryCustomizerBeanDefinition() {
    Class<?> beanType = ReactiveWebServerFactoryCustomizer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getReactiveWebServerFactoryCustomizerInstanceSupplier());
    return beanDefinition;
  }
}
