package org.springframework.boot.context.properties;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;

/**
 * Bean definitions for {@link ConfigurationPropertiesBinder}
 */
public class ConfigurationPropertiesBinder__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.context.internalConfigurationPropertiesBinder'.
   */
  private static BeanInstanceSupplier<ConfigurationPropertiesBinder> getInternalConfigurationPropertiesBinderInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ConfigurationPropertiesBinder>forConstructor(ApplicationContext.class)
            .withGenerator((registeredBean, args) -> new ConfigurationPropertiesBinder(args.get(0)));
  }

  /**
   * Get the bean definition for 'internalConfigurationPropertiesBinder'
   */
  public static BeanDefinition getInternalConfigurationPropertiesBinderBeanDefinition() {
    Class<?> beanType = ConfigurationPropertiesBinder.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getInternalConfigurationPropertiesBinderInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link ConfigurationPropertiesBinder.Factory}
   */
  public static class Factory__BeanDefinitions {
    /**
     * Get the bean definition for 'internalConfigurationPropertiesBinderFactory'
     */
    public static BeanDefinition getInternalConfigurationPropertiesBinderFactoryBeanDefinition() {
      Class<?> beanType = ConfigurationPropertiesBinder.Factory.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
      beanDefinition.setInstanceSupplier(ConfigurationPropertiesBinder.Factory::new);
      return beanDefinition;
    }
  }
}
