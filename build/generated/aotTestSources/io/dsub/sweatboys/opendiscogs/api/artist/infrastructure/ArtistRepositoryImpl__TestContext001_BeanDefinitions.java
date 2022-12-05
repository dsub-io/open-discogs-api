package io.dsub.sweatboys.opendiscogs.api.artist.infrastructure;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ArtistRepositoryImpl}
 */
public class ArtistRepositoryImpl__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'artistRepositoryImpl'.
   */
  private static BeanInstanceSupplier<ArtistRepositoryImpl> getArtistRepositoryImplInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ArtistRepositoryImpl>forConstructor(ArtistR2dbcRepository.class)
            .withGenerator((registeredBean, args) -> new ArtistRepositoryImpl(args.get(0)));
  }

  /**
   * Get the bean definition for 'artistRepositoryImpl'
   */
  public static BeanDefinition getArtistRepositoryImplBeanDefinition() {
    Class<?> beanType = ArtistRepositoryImpl.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getArtistRepositoryImplInstanceSupplier());
    return beanDefinition;
  }
}
