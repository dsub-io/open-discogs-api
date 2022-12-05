package org.springframework.boot.autoconfigure.web.reactive;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;

/**
 * Bean definitions for {@link HttpHandlerAutoConfiguration}
 */
public class HttpHandlerAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'httpHandlerAutoConfiguration'
   */
  public static BeanDefinition getHttpHandlerAutoConfigurationBeanDefinition() {
    Class<?> beanType = HttpHandlerAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(HttpHandlerAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link HttpHandlerAutoConfiguration.AnnotationConfig}
   */
  public static class AnnotationConfig__BeanDefinitions {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration$AnnotationConfig'.
     */
    private static BeanInstanceSupplier<HttpHandlerAutoConfiguration.AnnotationConfig> getAnnotationConfigInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<HttpHandlerAutoConfiguration.AnnotationConfig>forConstructor(ApplicationContext.class)
              .withGenerator((registeredBean, args) -> new HttpHandlerAutoConfiguration.AnnotationConfig(args.get(0)));
    }

    /**
     * Get the bean definition for 'annotationConfig'
     */
    public static BeanDefinition getAnnotationConfigBeanDefinition() {
      Class<?> beanType = HttpHandlerAutoConfiguration.AnnotationConfig.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getAnnotationConfigInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'httpHandler'.
     */
    private static BeanInstanceSupplier<HttpHandler> getHttpHandlerInstanceSupplier() {
      return BeanInstanceSupplier.<HttpHandler>forFactoryMethod(HttpHandlerAutoConfiguration.AnnotationConfig.class, "httpHandler", ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(HttpHandlerAutoConfiguration.AnnotationConfig.class).httpHandler(args.get(0)));
    }

    /**
     * Get the bean definition for 'httpHandler'
     */
    public static BeanDefinition getHttpHandlerBeanDefinition() {
      Class<?> beanType = HttpHandler.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getHttpHandlerInstanceSupplier());
      return beanDefinition;
    }
  }
}
