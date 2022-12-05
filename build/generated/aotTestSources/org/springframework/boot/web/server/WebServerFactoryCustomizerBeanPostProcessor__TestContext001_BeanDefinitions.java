package org.springframework.boot.web.server;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link WebServerFactoryCustomizerBeanPostProcessor}
 */
public class WebServerFactoryCustomizerBeanPostProcessor__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'webServerFactoryCustomizerBeanPostProcessor'
   */
  public static BeanDefinition getWebServerFactoryCustomizerBeanPostProcessorBeanDefinition() {
    Class<?> beanType = WebServerFactoryCustomizerBeanPostProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setSynthetic(true);
    beanDefinition.setInstanceSupplier(WebServerFactoryCustomizerBeanPostProcessor::new);
    return beanDefinition;
  }
}
