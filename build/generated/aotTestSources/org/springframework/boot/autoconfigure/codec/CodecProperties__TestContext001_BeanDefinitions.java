package org.springframework.boot.autoconfigure.codec;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CodecProperties}
 */
public class CodecProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'codecProperties'
   */
  public static BeanDefinition getCodecPropertiesBeanDefinition() {
    Class<?> beanType = CodecProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(CodecProperties::new);
    return beanDefinition;
  }
}
