package org.springframework.transaction.annotation;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.transaction.event.TransactionalEventListenerFactory;

/**
 * Bean definitions for {@link AbstractTransactionManagementConfiguration}
 */
public class AbstractTransactionManagementConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'internalTransactionalEventListenerFactory'
   */
  public static BeanDefinition getInternalTransactionalEventListenerFactoryBeanDefinition() {
    Class<?> beanType = TransactionalEventListenerFactory.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<TransactionalEventListenerFactory>forFactoryMethod(AbstractTransactionManagementConfiguration.class, "transactionalEventListenerFactory").withGenerator(AbstractTransactionManagementConfiguration::transactionalEventListenerFactory));
    return beanDefinition;
  }
}
