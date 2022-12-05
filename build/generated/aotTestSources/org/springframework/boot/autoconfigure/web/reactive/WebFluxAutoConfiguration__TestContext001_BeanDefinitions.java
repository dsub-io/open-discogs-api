package org.springframework.boot.autoconfigure.web.reactive;

import java.lang.Class;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.support.RouterFunctionMapping;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.server.session.WebSessionManager;

/**
 * Bean definitions for {@link WebFluxAutoConfiguration}
 */
public class WebFluxAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'webFluxAutoConfiguration'
   */
  public static BeanDefinition getWebFluxAutoConfigurationBeanDefinition() {
    Class<?> beanType = WebFluxAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(WebFluxAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Bean definitions for {@link WebFluxAutoConfiguration.WebFluxConfig}
   */
  public static class WebFluxConfig__BeanDefinitions {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration$WebFluxConfig'.
     */
    private static BeanInstanceSupplier<WebFluxAutoConfiguration.WebFluxConfig> getWebFluxConfigInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<WebFluxAutoConfiguration.WebFluxConfig>forConstructor(WebProperties.class, WebFluxProperties.class, ListableBeanFactory.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class)
              .withGenerator((registeredBean, args) -> new WebFluxAutoConfiguration.WebFluxConfig(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6)));
    }

    /**
     * Get the bean definition for 'webFluxConfig'
     */
    public static BeanDefinition getWebFluxConfigBeanDefinition() {
      Class<?> beanType = WebFluxAutoConfiguration.WebFluxConfig.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getWebFluxConfigInstanceSupplier());
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link WebFluxAutoConfiguration.EnableWebFluxConfiguration}
   */
  public static class EnableWebFluxConfiguration__BeanDefinitions {
    /**
     * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration$EnableWebFluxConfiguration'.
     */
    private static BeanInstanceSupplier<WebFluxAutoConfiguration.EnableWebFluxConfiguration> getEnableWebFluxConfigurationInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<WebFluxAutoConfiguration.EnableWebFluxConfiguration>forConstructor(WebFluxProperties.class, WebProperties.class, ServerProperties.class, ObjectProvider.class)
              .withGenerator((registeredBean, args) -> new WebFluxAutoConfiguration.EnableWebFluxConfiguration(args.get(0), args.get(1), args.get(2), args.get(3)));
    }

    /**
     * Get the bean definition for 'enableWebFluxConfiguration'
     */
    public static BeanDefinition getEnableWebFluxConfigurationBeanDefinition() {
      Class<?> beanType = WebFluxAutoConfiguration.EnableWebFluxConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      InstanceSupplier<WebFluxAutoConfiguration.EnableWebFluxConfiguration> instanceSupplier = getEnableWebFluxConfigurationInstanceSupplier();
      instanceSupplier = instanceSupplier.andThen(WebFluxAutoConfiguration_EnableWebFluxConfiguration__TestContext001_Autowiring::apply);
      beanDefinition.setInstanceSupplier(instanceSupplier);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'webFluxConversionService'.
     */
    private static BeanInstanceSupplier<FormattingConversionService> getWebFluxConversionServiceInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<FormattingConversionService>forFactoryMethod(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class, "webFluxConversionService")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class).webFluxConversionService());
    }

    /**
     * Get the bean definition for 'webFluxConversionService'
     */
    public static BeanDefinition getWebFluxConversionServiceBeanDefinition() {
      Class<?> beanType = FormattingConversionService.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getWebFluxConversionServiceInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'webFluxValidator'.
     */
    private static BeanInstanceSupplier<Validator> getWebFluxValidatorInstanceSupplier() {
      return BeanInstanceSupplier.<Validator>forFactoryMethod(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class, "webFluxValidator")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class).webFluxValidator());
    }

    /**
     * Get the bean definition for 'webFluxValidator'
     */
    public static BeanDefinition getWebFluxValidatorBeanDefinition() {
      Class<?> beanType = Validator.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getWebFluxValidatorInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'localeContextResolver'.
     */
    private static BeanInstanceSupplier<LocaleContextResolver> getLocaleContextResolverInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<LocaleContextResolver>forFactoryMethod(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class, "localeContextResolver")
              .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class).localeContextResolver());
    }

    /**
     * Get the bean definition for 'localeContextResolver'
     */
    public static BeanDefinition getLocaleContextResolverBeanDefinition() {
      Class<?> beanType = LocaleContextResolver.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getLocaleContextResolverInstanceSupplier());
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'webSessionManager'.
     */
    private static BeanInstanceSupplier<WebSessionManager> getWebSessionManagerInstanceSupplier() {
      return BeanInstanceSupplier.<WebSessionManager>forFactoryMethod(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class, "webSessionManager", ObjectProvider.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(WebFluxAutoConfiguration.EnableWebFluxConfiguration.class).webSessionManager(args.get(0)));
    }

    /**
     * Get the bean definition for 'webSessionManager'
     */
    public static BeanDefinition getWebSessionManagerBeanDefinition() {
      Class<?> beanType = WebSessionManager.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getWebSessionManagerInstanceSupplier());
      return beanDefinition;
    }
  }

  /**
   * Bean definitions for {@link WebFluxAutoConfiguration.WelcomePageConfiguration}
   */
  public static class WelcomePageConfiguration__BeanDefinitions {
    /**
     * Get the bean definition for 'welcomePageConfiguration'
     */
    public static BeanDefinition getWelcomePageConfigurationBeanDefinition() {
      Class<?> beanType = WebFluxAutoConfiguration.WelcomePageConfiguration.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(WebFluxAutoConfiguration.WelcomePageConfiguration::new);
      return beanDefinition;
    }

    /**
     * Get the bean instance supplier for 'welcomePageRouterFunctionMapping'.
     */
    private static BeanInstanceSupplier<RouterFunctionMapping> getWelcomePageRouterFunctionMappingInstanceSupplier(
        ) {
      return BeanInstanceSupplier.<RouterFunctionMapping>forFactoryMethod(WebFluxAutoConfiguration.WelcomePageConfiguration.class, "welcomePageRouterFunctionMapping", ApplicationContext.class, WebFluxProperties.class, WebProperties.class)
              .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(WebFluxAutoConfiguration.WelcomePageConfiguration.class).welcomePageRouterFunctionMapping(args.get(0), args.get(1), args.get(2)));
    }

    /**
     * Get the bean definition for 'welcomePageRouterFunctionMapping'
     */
    public static BeanDefinition getWelcomePageRouterFunctionMappingBeanDefinition() {
      Class<?> beanType = RouterFunctionMapping.class;
      RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
      beanDefinition.setInstanceSupplier(getWelcomePageRouterFunctionMappingInstanceSupplier());
      return beanDefinition;
    }
  }
}
