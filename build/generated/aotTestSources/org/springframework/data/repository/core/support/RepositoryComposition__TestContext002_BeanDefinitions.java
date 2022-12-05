package org.springframework.data.repository.core.support;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.ResolvableType;

/**
 * Bean definitions for {@link RepositoryComposition}
 */
public class RepositoryComposition__TestContext002_BeanDefinitions {
  /**
   * Bean definitions for {@link RepositoryComposition.RepositoryFragments}
   */
  public static class RepositoryFragments__BeanDefinitions {
    /**
     * Get the bean instance supplier for 'r2dbc.ArtistR2dbcRepository.fragments#0'.
     */
    private static BeanInstanceSupplier<RepositoryFragmentsFactoryBean> getFragmentsInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<RepositoryFragmentsFactoryBean>forConstructor(List.class)
              .withGenerator((registeredBean, args) -> new RepositoryFragmentsFactoryBean(args.get(0)));
    }

    /**
     * Get the bean definition for 'fragments#0'
     */
    public static BeanDefinition getFragmentsBeanDefinition() {
      ResolvableType beanType = ResolvableType.forClass(RepositoryFragmentsFactoryBean.class);
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
      beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, Collections.emptyList());
      beanDefinition.setInstanceSupplier(getFragmentsInstanceSupplier());
      return beanDefinition;
    }
  }
}
