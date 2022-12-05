package org.springframework.transaction.annotation;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Bean definitions for {@link ProxyTransactionManagementConfiguration}
 */
public class ProxyTransactionManagementConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'proxyTransactionManagementConfiguration'
   */
  public static BeanDefinition getProxyTransactionManagementConfigurationBeanDefinition() {
    Class<?> beanType = ProxyTransactionManagementConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    InstanceSupplier<ProxyTransactionManagementConfiguration> instanceSupplier = InstanceSupplier.using(ProxyTransactionManagementConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(ProxyTransactionManagementConfiguration__TestContext001_Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'org.springframework.transaction.config.internalTransactionAdvisor'.
   */
  private static BeanInstanceSupplier<BeanFactoryTransactionAttributeSourceAdvisor> getInternalTransactionAdvisorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<BeanFactoryTransactionAttributeSourceAdvisor>forFactoryMethod(ProxyTransactionManagementConfiguration.class, "transactionAdvisor", TransactionAttributeSource.class, TransactionInterceptor.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ProxyTransactionManagementConfiguration.class).transactionAdvisor(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'internalTransactionAdvisor'
   */
  public static BeanDefinition getInternalTransactionAdvisorBeanDefinition() {
    Class<?> beanType = BeanFactoryTransactionAttributeSourceAdvisor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getInternalTransactionAdvisorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'transactionAttributeSource'.
   */
  private static BeanInstanceSupplier<TransactionAttributeSource> getTransactionAttributeSourceInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<TransactionAttributeSource>forFactoryMethod(ProxyTransactionManagementConfiguration.class, "transactionAttributeSource")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(ProxyTransactionManagementConfiguration.class).transactionAttributeSource());
  }

  /**
   * Get the bean definition for 'transactionAttributeSource'
   */
  public static BeanDefinition getTransactionAttributeSourceBeanDefinition() {
    Class<?> beanType = TransactionAttributeSource.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getTransactionAttributeSourceInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'transactionInterceptor'.
   */
  private static BeanInstanceSupplier<TransactionInterceptor> getTransactionInterceptorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<TransactionInterceptor>forFactoryMethod(ProxyTransactionManagementConfiguration.class, "transactionInterceptor", TransactionAttributeSource.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(ProxyTransactionManagementConfiguration.class).transactionInterceptor(args.get(0)));
  }

  /**
   * Get the bean definition for 'transactionInterceptor'
   */
  public static BeanDefinition getTransactionInterceptorBeanDefinition() {
    Class<?> beanType = TransactionInterceptor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(getTransactionInterceptorInstanceSupplier());
    return beanDefinition;
  }
}
