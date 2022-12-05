package org.springframework.test.context.aot;

import io.dsub.sweatboys.opendiscogs.api.OpenDiscogsApiApplicationTests__TestContext001_ApplicationContextInitializer;
import io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistServiceTest__TestContext002_ApplicationContextInitializer;
import java.lang.Class;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Generated mappings for {@link AotTestContextInitializers}.
 */
public class AotTestContextInitializers__Generated {
  public static Map<String, Supplier<ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> getContextInitializers(
      ) {
    Map<String, Supplier<ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> map = new HashMap<>();
    map.put("io.dsub.sweatboys.opendiscogs.api.OpenDiscogsApiApplicationTests", () -> new OpenDiscogsApiApplicationTests__TestContext001_ApplicationContextInitializer());
    map.put("io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistServiceTest", () -> new ArtistServiceTest__TestContext002_ApplicationContextInitializer());
    return map;
  }

  public static Map<String, Class<? extends ApplicationContextInitializer<?>>> getContextInitializerClasses(
      ) {
    Map<String, Class<? extends ApplicationContextInitializer<?>>> map = new HashMap<>();
    map.put("io.dsub.sweatboys.opendiscogs.api.OpenDiscogsApiApplicationTests", OpenDiscogsApiApplicationTests__TestContext001_ApplicationContextInitializer.class);
    map.put("io.dsub.sweatboys.opendiscogs.api.artist.service.ArtistServiceTest", ArtistServiceTest__TestContext002_ApplicationContextInitializer.class);
    return map;
  }
}
