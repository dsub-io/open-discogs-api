package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.pool.ConnectionPool;
import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.io.ResourceLoader;

/**
 * Bean definitions for {@link ConnectionFactoryConfigurations}
 */
public class ConnectionFactoryConfigurations__TestContext002_BeanDefinitions {
  /**
   * Bean definitions for {@link ConnectionFactoryConfigurations.PoolConfiguration}
   */
  public static class PoolConfiguration__BeanDefinitions {
    /**
     * Get the bean definition for 'poolConfiguration'
     */
    public static BeanDefinition getPoolConfigurationBeanDefinition() {
      Class<?> beanType = ConnectionFactoryConfigurations.PoolConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(ConnectionFactoryConfigurations.PoolConfiguration::new);
      return beanDefinition;
    }

    /**
     * Bean definitions for {@link ConnectionFactoryConfigurations.PoolConfiguration.PooledConnectionFactoryConfiguration}
     */
    public static class PooledConnectionFactoryConfiguration__BeanDefinitions {
      /**
       * Get the bean definition for 'pooledConnectionFactoryConfiguration'
       */
      public static BeanDefinition getPooledConnectionFactoryConfigurationBeanDefinition() {
        Class<?> beanType = ConnectionFactoryConfigurations.PoolConfiguration.PooledConnectionFactoryConfiguration.class;
        RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
        beanDefinition.setInstanceSupplier(ConnectionFactoryConfigurations.PoolConfiguration.PooledConnectionFactoryConfiguration::new);
        return beanDefinition;
      }

      /**
       * Get the bean instance supplier for 'connectionFactory'.
       */
      private static BeanInstanceSupplier<ConnectionPool> getConnectionFactoryInstanceSupplier() {
        return BeanInstanceSupplier.<ConnectionPool>forFactoryMethod(ConnectionFactoryConfigurations.PoolConfiguration.PooledConnectionFactoryConfiguration.class, "connectionFactory", R2dbcProperties.class, ResourceLoader.class, ObjectProvider.class)
                .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ConnectionFactoryConfigurations.PoolConfiguration.PooledConnectionFactoryConfiguration.class).connectionFactory(args.get(0), args.get(1), args.get(2)));
      }

      /**
       * Get the bean definition for 'connectionFactory'
       */
      public static BeanDefinition getConnectionFactoryBeanDefinition() {
        Class<?> beanType = ConnectionPool.class;
        RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
        beanDefinition.setDestroyMethodNames("dispose");
        beanDefinition.setInstanceSupplier(getConnectionFactoryInstanceSupplier());
        return beanDefinition;
      }
    }
  }
}
