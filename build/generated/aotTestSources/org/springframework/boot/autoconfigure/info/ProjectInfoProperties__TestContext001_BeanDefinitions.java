package org.springframework.boot.autoconfigure.info;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ProjectInfoProperties}
 */
public class ProjectInfoProperties__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'projectInfoProperties'
   */
  public static BeanDefinition getProjectInfoPropertiesBeanDefinition() {
    Class<?> beanType = ProjectInfoProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ProjectInfoProperties::new);
    return beanDefinition;
  }
}
