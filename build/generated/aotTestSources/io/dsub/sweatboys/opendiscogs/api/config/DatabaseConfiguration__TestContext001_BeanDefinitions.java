package io.dsub.sweatboys.opendiscogs.api.config;

import io.dsub.sweatboys.opendiscogs.api.config.properties.DatabaseProperties;
import io.r2dbc.spi.ConnectionFactory;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.core.env.Environment;

/**
 * Bean definitions for {@link DatabaseConfiguration}
 */
public class DatabaseConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'databaseConfiguration'
   */
  public static BeanDefinition getDatabaseConfigurationBeanDefinition() {
    Class<?> beanType = DatabaseConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(DatabaseConfiguration.class);
    beanDefinition.setInstanceSupplier(DatabaseConfiguration$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'databaseProperties'.
   */
  private static BeanInstanceSupplier<DatabaseProperties> getDatabasePropertiesInstanceSupplier() {
    return BeanInstanceSupplier.<DatabaseProperties>forFactoryMethod(DatabaseConfiguration.class, "databaseProperties", Environment.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(DatabaseConfiguration.class).databaseProperties(args.get(0)));
  }

  /**
   * Get the bean definition for 'databaseProperties'
   */
  public static BeanDefinition getDatabasePropertiesBeanDefinition() {
    Class<?> beanType = DatabaseProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getDatabasePropertiesInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'connectionFactory'.
   */
  private static BeanInstanceSupplier<ConnectionFactory> getConnectionFactoryInstanceSupplier() {
    return BeanInstanceSupplier.<ConnectionFactory>forFactoryMethod(DatabaseConfiguration.class, "connectionFactory", DatabaseProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(DatabaseConfiguration.class).connectionFactory(args.get(0)));
  }

  /**
   * Get the bean definition for 'connectionFactory'
   */
  public static BeanDefinition getConnectionFactoryBeanDefinition() {
    Class<?> beanType = ConnectionFactory.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getConnectionFactoryInstanceSupplier());
    return beanDefinition;
  }
}
