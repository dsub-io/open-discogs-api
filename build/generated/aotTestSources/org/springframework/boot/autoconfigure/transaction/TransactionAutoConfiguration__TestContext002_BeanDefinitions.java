package org.springframework.boot.autoconfigure.transaction;

import java.lang.Class;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * Bean definitions for {@link TransactionAutoConfiguration}
 */
public class TransactionAutoConfiguration__TestContext002_BeanDefinitions {
  /**
   * Get the bean definition for 'transactionAutoConfiguration'
   */
  public static BeanDefinition getTransactionAutoConfigurationBeanDefinition() {
    Class<?> beanType = TransactionAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(TransactionAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'platformTransactionManagerCustomizers'.
   */
  private static BeanInstanceSupplier<TransactionManagerCustomizers> getPlatformTransactionManagerCustomizersInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<TransactionManagerCustomizers>forFactoryMethod(TransactionAutoConfiguration.class, "platformTransactionManagerCustomizers", ObjectProvider.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(TransactionAutoConfiguration.class).platformTransactionManagerCustomizers(args.get(0)));
  }

  /**
   * Get the bean definition for 'platformTransactionManagerCustomizers'
   */
  public static BeanDefinition getPlatformTransactionManagerCustomizersBeanDefinition() {
    Class<?> beanType = TransactionManagerCustomizers.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getPlatformTransactionManagerCustomizersInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'transactionalOperator'.
   */
  private static BeanInstanceSupplier<TransactionalOperator> getTransactionalOperatorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<TransactionalOperator>forFactoryMethod(TransactionAutoConfiguration.class, "transactionalOperator", ReactiveTransactionManager.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(TransactionAutoConfiguration.class).transactionalOperator(args.get(0)));
  }

  /**
   * Get the bean definition for 'transactionalOperator'
   */
  public static BeanDefinition getTransactionalOperatorBeanDefinition() {
    Class<?> beanType = TransactionalOperator.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getTransactionalOperatorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link TransactionAutoConfiguration.EnableTransactionManagementConfiguration}
   */
  public static class EnableTransactionManagementConfiguration__BeanDefinitions {
    /**
     * Get the bean definition for 'enableTransactionManagementConfiguration'
     */
    public static BeanDefinition getEnableTransactionManagementConfigurationBeanDefinition() {
      Class<?> beanType = TransactionAutoConfiguration.EnableTransactionManagementConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(TransactionAutoConfiguration.EnableTransactionManagementConfiguration::new);
      return beanDefinition;
    }

    /**
     * Bean definitions for {@link TransactionAutoConfiguration.EnableTransactionManagementConfiguration.CglibAutoProxyConfiguration}
     */
    public static class CglibAutoProxyConfiguration__BeanDefinitions {
      /**
       * Get the bean definition for 'cglibAutoProxyConfiguration'
       */
      public static BeanDefinition getCglibAutoProxyConfigurationBeanDefinition() {
        Class<?> beanType = TransactionAutoConfiguration.EnableTransactionManagementConfiguration.CglibAutoProxyConfiguration.class;
        RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
        beanDefinition.setInstanceSupplier(TransactionAutoConfiguration.EnableTransactionManagementConfiguration.CglibAutoProxyConfiguration::new);
        return beanDefinition;
      }
    }
  }
}
