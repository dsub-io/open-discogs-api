package io.dsub.sweatboys.opendiscogs.api.artist.controller;

import io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ArtistController}
 */
public class ArtistController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'artistController'.
   */
  private static BeanInstanceSupplier<ArtistController> getArtistControllerInstanceSupplier() {
    return BeanInstanceSupplier.<ArtistController>forConstructor(ArtistService.class)
            .withGenerator((registeredBean, args) -> new ArtistController(args.get(0)));
  }

  /**
   * Get the bean definition for 'artistController'
   */
  public static BeanDefinition getArtistControllerBeanDefinition() {
    Class<?> beanType = ArtistController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getArtistControllerInstanceSupplier());
    return beanDefinition;
  }
}
