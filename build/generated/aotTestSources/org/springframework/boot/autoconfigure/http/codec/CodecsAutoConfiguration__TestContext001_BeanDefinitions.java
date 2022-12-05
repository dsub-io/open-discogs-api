package org.springframework.boot.autoconfigure.http.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.codec.CodecProperties;
import org.springframework.boot.web.codec.CodecCustomizer;

/**
 * Bean definitions for {@link CodecsAutoConfiguration}
 */
public class CodecsAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'codecsAutoConfiguration'
   */
  public static BeanDefinition getCodecsAutoConfigurationBeanDefinition() {
    Class<?> beanType = CodecsAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(CodecsAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link CodecsAutoConfiguration.JacksonCodecConfiguration}
   */
  public static class JacksonCodecConfiguration__BeanDefinitions {
    /**
     * Get the bean definition for 'jacksonCodecConfiguration'
     */
    public static BeanDefinition getJacksonCodecConfigurationBeanDefinition() {
      Class<?> beanType = CodecsAutoConfiguration.JacksonCodecConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(CodecsAutoConfiguration.JacksonCodecConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'jacksonCodecCustomizer'.
     */
    private static BeanInstanceSupplier<CodecCustomizer> getJacksonCodecCustomizerInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<CodecCustomizer>forFactoryMethod(CodecsAutoConfiguration.JacksonCodecConfiguration.class, "jacksonCodecCustomizer", ObjectMapper.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(CodecsAutoConfiguration.JacksonCodecConfiguration.class).jacksonCodecCustomizer(args.get(0)));
    }

    /**
     * Get the bean definition for 'jacksonCodecCustomizer'
     */
    public static BeanDefinition getJacksonCodecCustomizerBeanDefinition() {
      Class<?> beanType = CodecCustomizer.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getJacksonCodecCustomizerInstanceSupplier());
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link CodecsAutoConfiguration.DefaultCodecsConfiguration}
   */
  public static class DefaultCodecsConfiguration__BeanDefinitions {
    /**
     * Get the bean definition for 'defaultCodecsConfiguration'
     */
    public static BeanDefinition getDefaultCodecsConfigurationBeanDefinition() {
      Class<?> beanType = CodecsAutoConfiguration.DefaultCodecsConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(CodecsAutoConfiguration.DefaultCodecsConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'defaultCodecCustomizer'.
     */
    private static BeanInstanceSupplier<CodecCustomizer> getDefaultCodecCustomizerInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<CodecCustomizer>forFactoryMethod(CodecsAutoConfiguration.DefaultCodecsConfiguration.class, "defaultCodecCustomizer", CodecProperties.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(CodecsAutoConfiguration.DefaultCodecsConfiguration.class).defaultCodecCustomizer(args.get(0)));
    }

    /**
     * Get the bean definition for 'defaultCodecCustomizer'
     */
    public static BeanDefinition getDefaultCodecCustomizerBeanDefinition() {
      Class<?> beanType = CodecCustomizer.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getDefaultCodecCustomizerInstanceSupplier());
      return beanDefinition;
    }
  }
}
