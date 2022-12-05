package org.springframework.boot.autoconfigure.sql.init;

import io.r2dbc.spi.ConnectionFactory;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link R2dbcInitializationConfiguration}
 */
public class R2dbcInitializationConfiguration__TestContext002_BeanDefinitions {
  /**
   * Get the bean definition for 'r2dbcInitializationConfiguration'
   */
  public static BeanDefinition getRdbcInitializationConfigurationBeanDefinition() {
    Class<?> beanType = R2dbcInitializationConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(R2dbcInitializationConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'r2dbcScriptDatabaseInitializer'.
   */
  private static BeanInstanceSupplier<SqlR2dbcScriptDatabaseInitializer> getRdbcScriptDatabaseInitializerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SqlR2dbcScriptDatabaseInitializer>forFactoryMethod(R2dbcInitializationConfiguration.class, "r2dbcScriptDatabaseInitializer", ConnectionFactory.class, SqlInitializationProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(R2dbcInitializationConfiguration.class).r2dbcScriptDatabaseInitializer(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'r2dbcScriptDatabaseInitializer'
   */
  public static BeanDefinition getRdbcScriptDatabaseInitializerBeanDefinition() {
    Class<?> beanType = SqlR2dbcScriptDatabaseInitializer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRdbcScriptDatabaseInitializerInstanceSupplier());
    return beanDefinition;
  }
}
