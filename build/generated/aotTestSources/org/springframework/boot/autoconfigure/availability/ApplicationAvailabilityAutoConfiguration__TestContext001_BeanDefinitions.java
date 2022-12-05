package org.springframework.boot.autoconfigure.availability;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.availability.ApplicationAvailabilityBean;

/**
 * Bean definitions for {@link ApplicationAvailabilityAutoConfiguration}
 */
public class ApplicationAvailabilityAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'applicationAvailabilityAutoConfiguration'
   */
  public static BeanDefinition getApplicationAvailabilityAutoConfigurationBeanDefinition() {
    Class<?> beanType = ApplicationAvailabilityAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ApplicationAvailabilityAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'applicationAvailability'.
   */
  private static BeanInstanceSupplier<ApplicationAvailabilityBean> getApplicationAvailabilityInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ApplicationAvailabilityBean>forFactoryMethod(ApplicationAvailabilityAutoConfiguration.class, "applicationAvailability")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(ApplicationAvailabilityAutoConfiguration.class).applicationAvailability());
  }

  /**
   * Get the bean definition for 'applicationAvailability'
   */
  public static BeanDefinition getApplicationAvailabilityBeanDefinition() {
    Class<?> beanType = ApplicationAvailabilityBean.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getApplicationAvailabilityInstanceSupplier());
    return beanDefinition;
  }
}
