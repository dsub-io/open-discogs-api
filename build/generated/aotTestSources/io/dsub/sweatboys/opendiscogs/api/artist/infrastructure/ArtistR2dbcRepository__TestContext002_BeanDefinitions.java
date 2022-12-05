package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import java.lang.Class;
import java.lang.Object;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.ResolvableType;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactoryBean;
import org.springframework.data.repository.query.QueryLookupStrategy;

/**
 * Bean definitions for {@link ArtistR2dbcRepository}
 */
public class ArtistR2dbcRepository__TestContext002_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'artistR2dbcRepository'.
   */
  private static BeanInstanceSupplier<R2dbcRepositoryFactoryBean> getArtistRdbcRepositoryInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<R2dbcRepositoryFactoryBean>forConstructor(Class.class)
            .withGenerator((registeredBean, args) -> new R2dbcRepositoryFactoryBean(args.get(0)));
  }

  /**
   * Get the bean definition for 'artistR2dbcRepository'
   */
  public static BeanDefinition getArtistRdbcRepositoryBeanDefinition() {
    ResolvableType beanType = ResolvableType.forClassWithGenerics(R2dbcRepositoryFactoryBean.class, ArtistR2dbcRepository.class, Object.class, Object.class);
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setLazyInit(false);
    beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, "io.dsub.sweatboys.opendiscogs.api.artist.infrastructure.ArtistR2dbcRepository");
    beanDefinition.getPropertyValues().addPropertyValue("queryLookupStrategyKey", QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND);
    beanDefinition.getPropertyValues().addPropertyValue("lazyInit", false);
    beanDefinition.getPropertyValues().addPropertyValue("namedQueries", new RuntimeBeanReference("r2dbc.named-queries#0"));
    beanDefinition.getPropertyValues().addPropertyValue("repositoryFragments", new RuntimeBeanReference("r2dbc.ArtistR2dbcRepository.fragments#0"));
    beanDefinition.getPropertyValues().addPropertyValue("entityOperations", new RuntimeBeanReference("r2dbcEntityTemplate"));
    beanDefinition.setInstanceSupplier(getArtistRdbcRepositoryInstanceSupplier());
    return beanDefinition;
  }
}
