package org.springframework.boot.autoconfigure.transaction;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TransactionProperties}
 */
public class TransactionProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'transactionProperties'
   */
  public static BeanDefinition getTransactionPropertiesBeanDefinition() {
    Class<?> beanType = TransactionProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(TransactionProperties::new);
    return beanDefinition;
  }
}
