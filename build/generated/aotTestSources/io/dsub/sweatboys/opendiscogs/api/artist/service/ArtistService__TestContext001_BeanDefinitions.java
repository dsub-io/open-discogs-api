package io.dsub.sweatboys.opendiscogs.api.artist.service;

import io.dsub.sweatboys.opendiscogs.api.artist.domain.ArtistRepository;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ArtistService}
 */
public class ArtistService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'artistService'.
   */
  private static BeanInstanceSupplier<ArtistService> getArtistServiceInstanceSupplier() {
    return BeanInstanceSupplier.<ArtistService>forConstructor(ArtistRepository.class)
            .withGenerator((registeredBean, args) -> new ArtistService(args.get(0)));
  }

  /**
   * Get the bean definition for 'artistService'
   */
  public static BeanDefinition getArtistServiceBeanDefinition() {
    Class<?> beanType = ArtistService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getArtistServiceInstanceSupplier());
    return beanDefinition;
  }
}
