package org.springframework.boot.autoconfigure.web.reactive.error;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.http.codec.ServerCodecConfigurer;

/**
 * Bean definitions for {@link ErrorWebFluxAutoConfiguration}
 */
public class ErrorWebFluxAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration'.
   */
  private static BeanInstanceSupplier<ErrorWebFluxAutoConfiguration> getErrorWebFluxAutoConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ErrorWebFluxAutoConfiguration>forConstructor(ServerProperties.class)
            .withGenerator((registeredBean, args) -> new ErrorWebFluxAutoConfiguration(args.get(0)));
  }

  /**
   * Get the bean definition for 'errorWebFluxAutoConfiguration'
   */
  public static BeanDefinition getErrorWebFluxAutoConfigurationBeanDefinition() {
    Class<?> beanType = ErrorWebFluxAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getErrorWebFluxAutoConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'errorWebExceptionHandler'.
   */
  private static BeanInstanceSupplier<ErrorWebExceptionHandler> getErrorWebExceptionHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ErrorWebExceptionHandler>forFactoryMethod(ErrorWebFluxAutoConfiguration.class, "errorWebExceptionHandler", ErrorAttributes.class, WebProperties.class, ObjectProvider.class, ServerCodecConfigurer.class, ApplicationContext.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ErrorWebFluxAutoConfiguration.class).errorWebExceptionHandler(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'errorWebExceptionHandler'
   */
  public static BeanDefinition getErrorWebExceptionHandlerBeanDefinition() {
    Class<?> beanType = ErrorWebExceptionHandler.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getErrorWebExceptionHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'errorAttributes'.
   */
  private static BeanInstanceSupplier<DefaultErrorAttributes> getErrorAttributesInstanceSupplier() {
    return BeanInstanceSupplier.<DefaultErrorAttributes>forFactoryMethod(ErrorWebFluxAutoConfiguration.class, "errorAttributes")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(ErrorWebFluxAutoConfiguration.class).errorAttributes());
  }

  /**
   * Get the bean definition for 'errorAttributes'
   */
  public static BeanDefinition getErrorAttributesBeanDefinition() {
    Class<?> beanType = DefaultErrorAttributes.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getErrorAttributesInstanceSupplier());
    return beanDefinition;
  }
}
