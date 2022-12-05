package org.springframework.boot.autoconfigure.aop;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AopAutoConfiguration}
 */
public class AopAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'aopAutoConfiguration'
   */
  public static BeanDefinition getAopAutoConfigurationBeanDefinition() {
    Class<?> beanType = AopAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(AopAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link AopAutoConfiguration.ClassProxyingConfiguration}
   */
  public static class ClassProxyingConfiguration__BeanDefinitions {
    /**
     * Get the bean definition for 'classProxyingConfiguration'
     */
    public static BeanDefinition getClassProxyingConfigurationBeanDefinition() {
      Class<?> beanType = AopAutoConfiguration.ClassProxyingConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(AopAutoConfiguration.ClassProxyingConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean definition for 'forceAutoProxyCreatorToUseClassProxying'
     */
    public static BeanDefinition getForceAutoProxyCreatorToUseClassProxyingBeanDefinition() {
      Class<?> beanType = BeanFactoryPostProcessor.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<BeanFactoryPostProcessor>forFactoryMethod(AopAutoConfiguration.ClassProxyingConfiguration.class, "forceAutoProxyCreatorToUseClassProxying").withGenerator(AopAutoConfiguration.ClassProxyingConfiguration::forceAutoProxyCreatorToUseClassProxying));
      return beanDefinition;
    }
  }
}
