package org.springframework.boot.autoconfigure;

import java.lang.Class;
import java.lang.String;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AutoConfigurationPackages}
 */
public class AutoConfigurationPackages__TestContext002_BeanDefinitions {
  /**
   * Bean definitions for {@link AutoConfigurationPackages.BasePackages}
   */
  public static class BasePackages__BeanDefinitions {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.AutoConfigurationPackages'.
     */
    private static BeanInstanceSupplier<AutoConfigurationPackages.BasePackages> getAutoConfigurationPackagesInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<AutoConfigurationPackages.BasePackages>forConstructor(String[].class)
              .withGenerator((registeredBean, args) -> new AutoConfigurationPackages.BasePackages(args.get(0)));
    }

    /**
     * Get the bean definition for 'autoConfigurationPackages'
     */
    public static BeanDefinition getAutoConfigurationPackagesBeanDefinition() {
      Class<?> beanType = AutoConfigurationPackages.BasePackages.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
      beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, new String[] {"io.dsub.sweatboys.opendiscogs.api"});
      beanDefinition.setInstanceSupplier(getAutoConfigurationPackagesInstanceSupplier());
      return beanDefinition;
    }
  }
}
