package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;

/**
 * Bean definitions for {@link R2dbcTransactionManagerAutoConfiguration}
 */
public class R2dbcTransactionManagerAutoConfiguration__TestContext002_BeanDefinitions {
  /**
   * Get the bean definition for 'r2dbcTransactionManagerAutoConfiguration'
   */
  public static BeanDefinition getRdbcTransactionManagerAutoConfigurationBeanDefinition() {
    Class<?> beanType = R2dbcTransactionManagerAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(R2dbcTransactionManagerAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'connectionFactoryTransactionManager'.
   */
  private static BeanInstanceSupplier<R2dbcTransactionManager> getConnectionFactoryTransactionManagerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<R2dbcTransactionManager>forFactoryMethod(R2dbcTransactionManagerAutoConfiguration.class, "connectionFactoryTransactionManager", ConnectionFactory.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(R2dbcTransactionManagerAutoConfiguration.class).connectionFactoryTransactionManager(args.get(0)));
  }

  /**
   * Get the bean definition for 'connectionFactoryTransactionManager'
   */
  public static BeanDefinition getConnectionFactoryTransactionManagerBeanDefinition() {
    Class<?> beanType = R2dbcTransactionManager.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getConnectionFactoryTransactionManagerInstanceSupplier());
    return beanDefinition;
  }
}
