package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.r2dbc.core.DatabaseClient;

/**
 * Bean definitions for {@link ConnectionFactoryDependentConfiguration}
 */
public class ConnectionFactoryDependentConfiguration__TestContext002_BeanDefinitions {
  /**
   * Get the bean definition for 'connectionFactoryDependentConfiguration'
   */
  public static BeanDefinition getConnectionFactoryDependentConfigurationBeanDefinition() {
    Class<?> beanType = ConnectionFactoryDependentConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ConnectionFactoryDependentConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'r2dbcDatabaseClient'.
   */
  private static BeanInstanceSupplier<DatabaseClient> getRdbcDatabaseClientInstanceSupplier() {
    return BeanInstanceSupplier.<DatabaseClient>forFactoryMethod(ConnectionFactoryDependentConfiguration.class, "r2dbcDatabaseClient", ConnectionFactory.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ConnectionFactoryDependentConfiguration.class).r2dbcDatabaseClient(args.get(0)));
  }

  /**
   * Get the bean definition for 'r2dbcDatabaseClient'
   */
  public static BeanDefinition getRdbcDatabaseClientBeanDefinition() {
    Class<?> beanType = DatabaseClient.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRdbcDatabaseClientInstanceSupplier());
    return beanDefinition;
  }
}
