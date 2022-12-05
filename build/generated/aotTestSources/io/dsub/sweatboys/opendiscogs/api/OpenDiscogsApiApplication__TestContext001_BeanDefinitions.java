package io.dsub.sweatboys.opendiscogs.api;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link OpenDiscogsApiApplication}
 */
public class OpenDiscogsApiApplication__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'openDiscogsApiApplication'
   */
  public static BeanDefinition getOpenDiscogsApiApplicationBeanDefinition() {
    Class<?> beanType = OpenDiscogsApiApplication.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(OpenDiscogsApiApplication.class);
    beanDefinition.setInstanceSupplier(OpenDiscogsApiApplication$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
