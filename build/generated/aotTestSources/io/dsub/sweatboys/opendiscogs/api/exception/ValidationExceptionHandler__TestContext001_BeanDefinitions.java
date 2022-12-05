package io.dsub.sweatboys.opendiscogs.api.exception;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ValidationExceptionHandler}
 */
public class ValidationExceptionHandler__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'validationExceptionHandler'
   */
  public static BeanDefinition getValidationExceptionHandlerBeanDefinition() {
    Class<?> beanType = ValidationExceptionHandler.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ValidationExceptionHandler::new);
    return beanDefinition;
  }
}
