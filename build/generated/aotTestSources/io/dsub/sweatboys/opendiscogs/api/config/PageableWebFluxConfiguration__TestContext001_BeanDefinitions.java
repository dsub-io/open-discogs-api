package io.dsub.sweatboys.opendiscogs.api.config;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link PageableWebFluxConfiguration}
 */
public class PageableWebFluxConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'pageableWebFluxConfiguration'
   */
  public static BeanDefinition getPageableWebFluxConfigurationBeanDefinition() {
    Class<?> beanType = PageableWebFluxConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(PageableWebFluxConfiguration.class);
    beanDefinition.setInstanceSupplier(PageableWebFluxConfiguration$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
